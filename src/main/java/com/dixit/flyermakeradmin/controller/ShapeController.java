package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.constant.CategoryTypeConstant;
import com.dixit.flyermakeradmin.entity.Shape;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
import com.dixit.flyermakeradmin.service.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/shape")
public class ShapeController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ShapeController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    ShapeService shapeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ShapeTagMappingService shapeTagMappingService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertShape(@RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Shape res = shapeService.insertNewRecord(image, catId, isPurchase);
            shapeTagMappingService.insertNewRecord(res.getShapeId(), tags);
            logger.debug("Shape created with id : {}", res.getShapeId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void catIdPresentAndValidate(MultipartFile image, Integer catId, String isPurchase) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_SHAPE))) {
            logger.error("Category Id: {} Not Exist in Database for Type = Shape", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for Type = Shape");
        }
        if(Boolean.FALSE.equals(validateInputParam.shapePost(image, isPurchase))) {
            logger.error("Input Parameter not valid for Shape Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }

    @PostMapping(value = "/delete/{shapeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteShape(@PathVariable Integer shapeId) {
        if(Boolean.FALSE.equals(shapeService.checkIfShapeIdPresent(shapeId))) {
            logger.error("Shape Id: {} Not Exist in Database", shapeId);
            throw new DataNotInDatabaseException("Shape Id Not Exist in Database");
        }
        try {
            shapeService.deleteByShapeId(shapeId);
            shapeTagMappingService.deleteByShapeId(shapeId);
            DeleteResponse res = new DeleteResponse("Shape deleted with id : " + shapeId);
            logger.debug("Shape deleted with id : {}", shapeId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateShape(@RequestParam("shapeId") Integer shapeId,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        Optional<Shape> shape = shapeService.findByShapeId(shapeId);
        if(shape.isEmpty()) {
            logger.error("Shape Id: {} Not Exist in Database", shapeId);
            throw new DataNotInDatabaseException("Shape Id Not Exist in Database");
        }
        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Shape res = shapeService.updateRecord(shape.get(), image, catId, isPurchase);
            shapeTagMappingService.deleteByShapeId(res.getShapeId());
            shapeTagMappingService.insertNewRecord(res.getShapeId(), tags);
            logger.debug("Shape updated with id : {}", res.getShapeId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
