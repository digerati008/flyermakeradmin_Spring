package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.constant.CategoryTypeConstant;
import com.dixit.flyermakeradmin.entity.Background;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
import com.dixit.flyermakeradmin.service.BackgroundService;
import com.dixit.flyermakeradmin.service.BackgroundTagMappingService;
import com.dixit.flyermakeradmin.service.CategoryService;
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
@RequestMapping("/background")
public class BackgroundController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(BackgroundController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    BackgroundService backgroundService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BackgroundTagMappingService backgroundTagMappingService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertBackground(@RequestParam("image") MultipartFile image,
                                               @RequestParam("catId") Integer catId,
                                               @RequestParam("isPurchase") String isPurchase,
                                               @RequestParam("tags") Integer[] tags) {

        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Background res = backgroundService.insertNewRecord(image, catId, isPurchase);
            backgroundTagMappingService.insertNewRecord(res.getBgId(), tags);
            logger.debug("Background created with id : {}", res.getBgId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void catIdPresentAndValidate(MultipartFile image, Integer catId, String isPurchase) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_BACKGROUND))) {
            logger.error("Category Id: {} Not Exist in Database for Type = Background", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for Type = Background");
        }
        if(Boolean.FALSE.equals(validateInputParam.backgroundPost(image, isPurchase))) {
            logger.error("Input Parameter not valid for Background Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }

    @PostMapping(value = "/delete/{bgId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBackground(@PathVariable Integer bgId) {
        if(Boolean.FALSE.equals(backgroundService.checkIfBgIdPresent(bgId))) {
            logger.error("Background Id: {} Not Exist in Database", bgId);
            throw new DataNotInDatabaseException("Background Id Not Exist in Database");
        }
        try {
            backgroundService.deleteByBgId(bgId);
            backgroundTagMappingService.deleteByBgId(bgId);
            DeleteResponse res = new DeleteResponse("Background deleted with id : " + bgId);
            logger.debug("Background deleted with id : {}", bgId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateBackground(@RequestParam("bgId") Integer bgId,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("catId") Integer catId,
                                                   @RequestParam("isPurchase") String isPurchase,
                                                   @RequestParam("tags") Integer[] tags) {

        Optional<Background> background = backgroundService.findByBgId(bgId);
        if(background.isEmpty()) {
            logger.error("Background Id: {} Not Exist in Database", bgId);
            throw new DataNotInDatabaseException("Background Id Not Exist in Database");
        }
        catIdPresentAndValidate(image, catId, isPurchase);
        try {
            Background res = backgroundService.updateRecord(background.get(), image, catId, isPurchase);
            backgroundTagMappingService.deleteByBgId(res.getBgId());
            backgroundTagMappingService.insertNewRecord(res.getBgId(), tags);
            logger.debug("Background updated with id : {}", res.getBgId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
