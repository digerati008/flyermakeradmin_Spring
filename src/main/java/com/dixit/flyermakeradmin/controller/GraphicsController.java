package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.constant.CategoryTypeConstant;
import com.dixit.flyermakeradmin.entity.Graphics;
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

@CrossOrigin
@RestController
@RequestMapping("/graphics")
public class GraphicsController {
    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(GraphicsController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    GraphicsService graphicsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GraphicsTagMappingService graphicsTagMappingService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertGraphics(@RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Graphics res = graphicsService.insertNewRecord(image, catId, isPurchase);
            graphicsTagMappingService.insertNewRecord(res.getGraphicsId(), tags);
            logger.debug("Graphics created with id : {}", res.getGraphicsId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/delete/{graphicsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteGraphics(@PathVariable Integer graphicsId) {
        if(Boolean.FALSE.equals(graphicsService.checkIfGraphicsIdPresent(graphicsId))) {
            logger.error("Graphics Id: {} Not Exist in Database", graphicsId);
            throw new DataNotInDatabaseException("Graphics Id Not Exist in Database");
        }
        try {
            graphicsService.deleteByGraphicsId(graphicsId);
            graphicsTagMappingService.deleteByGraphicsId(graphicsId);
            DeleteResponse res = new DeleteResponse("Graphics deleted with id : " + graphicsId);
            logger.debug("Graphics deleted with id : {}", graphicsId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateGraphics(@RequestParam("graphicsId") Integer graphicsId,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        Optional<Graphics> graphics = graphicsService.findByGraphicsId(graphicsId);
        if(graphics.isEmpty()) {
            logger.error("Graphics Id: {} Not Exist in Database", graphics);
            throw new DataNotInDatabaseException("Graphics Id Not Exist in Database");
        }
        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Graphics res = graphicsService.updateRecord(graphics.get(), image, catId, isPurchase);
            graphicsTagMappingService.deleteByGraphicsId(res.getGraphicsId());
            graphicsTagMappingService.insertNewRecord(res.getGraphicsId(), tags);
            logger.debug("Graphics updated with id : {}", res.getGraphicsId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void catIdPresentAndValidate(MultipartFile image, Integer catId, String isPurchase) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_GRAPHICS))) {
            logger.error("Category Id: {} Not Exist in Database for Type = Graphics", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for Type = Graphics");
        }
        if(Boolean.FALSE.equals(validateInputParam.graphicsPost(image, isPurchase))) {
            logger.error("Input Parameter not valid for Graphics Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }
}
