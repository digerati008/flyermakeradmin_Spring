package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.constant.CategoryTypeConstant;
import com.dixit.flyermakeradmin.entity.Textart;
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
@RequestMapping("/textart")
public class TextartController {
    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(TextartController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    TextartService textartService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TextartTagMappingService textartTagMappingService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertTextart(@RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Textart res = textartService.insertNewRecord(image, catId, isPurchase);
            textartTagMappingService.insertNewRecord(res.getTextartId(), tags);
            logger.debug("Textart created with id : {}", res.getTextartId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void catIdPresentAndValidate(MultipartFile image, Integer catId, String isPurchase) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_TEXTART))) {
            logger.error("Category Id: {} Not Exist in Database for Type = Textart", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for Type = Textart");
        }
        if(Boolean.FALSE.equals(validateInputParam.textartPost(image, isPurchase))) {
            logger.error("Input Parameter not valid for Textart Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }

    @PostMapping(value = "/delete/{textartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTextart(@PathVariable Integer textartId) {
        if(Boolean.FALSE.equals(textartService.checkIfTextartIdPresent(textartId))) {
            logger.error("Textart Id: {} Not Exist in Database", textartId);
            throw new DataNotInDatabaseException("Textart Id Not Exist in Database");
        }
        try {
            textartService.deleteByTextartId(textartId);
            textartTagMappingService.deleteByTextartId(textartId);
            DeleteResponse res = new DeleteResponse("Textart deleted with id : " + textartId);
            logger.debug("Textart deleted with id : {}", textartId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTextart(@RequestParam("textartId") Integer textartId,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        Optional<Textart> textart = textartService.findByTextartId(textartId);
        if(textart.isEmpty()) {
            logger.error("Textart Id: {} Not Exist in Database", textart);
            throw new DataNotInDatabaseException("Textart Id Not Exist in Database");
        }
        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Textart res = textartService.updateRecord(textart.get(), image, catId, isPurchase);
            textartTagMappingService.deleteByTextartId(res.getTextartId());
            textartTagMappingService.insertNewRecord(res.getTextartId(), tags);
            logger.debug("Textart updated with id : {}", res.getTextartId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
