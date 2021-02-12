package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.constant.CategoryTypeConstant;
import com.dixit.flyermakeradmin.entity.Poster;
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
@RequestMapping("/poster")
public class PosterController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(PosterController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    PosterService posterService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PosterTagMappingService posterTagMappingService;

    @Autowired
    PosterImageMappingService posterImageMappingService;

    @Autowired
    PosterTextMappingService posterTextMappingService;

    @Autowired
    TextService textService;

    @Autowired
    ImageService imageService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertPoster(@RequestParam("thumbImage") MultipartFile thumbImage,
                                               @RequestParam("bgImage") MultipartFile bgImage,
                                               @RequestParam("catId") Integer catId,
                                               @RequestParam("isPurchase") String isPurchase,
                                               @RequestParam("height") Integer height,
                                               @RequestParam("width") Integer width,
                                               @RequestParam("status") String status,
                                               @RequestParam("tags") Integer[] tags) {

        catIdPresentAndValidate(thumbImage, bgImage, catId, isPurchase, status);
        try {
            Poster res = posterService.insertNewRecord(thumbImage, bgImage, isPurchase, height, width, status, catId);
            posterTagMappingService.insertNewRecord(res.getPosterId(), tags);
            logger.debug("Poster created with id : {}", res.getPosterId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void catIdPresentAndValidate(MultipartFile thumbImage, MultipartFile bgImage, Integer catId, String isPurchase, String status) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_POSTER))) {
            logger.error("Category Id: {} Not Exist in Database for Type = Poster", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for Type = Poster");
        }
        if(Boolean.FALSE.equals(validateInputParam.posterPost(thumbImage, bgImage, isPurchase, status))) {
            logger.error("Input Parameter not valid for Poster Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }

    @PostMapping(value = "/delete/{posterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePoster(@PathVariable Integer posterId) {
        if(Boolean.FALSE.equals(posterService.checkIfPosterIdPresent(posterId))) {
            logger.error("Poster Id: {} Not Exist in Database", posterId);
            throw new DataNotInDatabaseException("Poster Id Not Exist in Database");
        }
        try {
            posterService.deleteByPosterId(posterId);
            posterTagMappingService.deleteByPosterId(posterId);
            textService.deleteByPosterId(posterId);
            posterTextMappingService.deleteByPosterId(posterId);
            imageService.deleteByPosterId(posterId);
            posterImageMappingService.deleteByPosterId(posterId);
            DeleteResponse res = new DeleteResponse("poster deleted with id : "+posterId);
            logger.debug("Poster deleted with id : {}", posterId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePoster(@RequestParam("posterId") Integer posterId,
                                               @RequestParam("thumbImage") MultipartFile thumbImage,
                                               @RequestParam("bgImage") MultipartFile bgImage,
                                               @RequestParam("catId") Integer catId,
                                               @RequestParam("isPurchase") String isPurchase,
                                               @RequestParam("height") Integer height,
                                               @RequestParam("width") Integer width,
                                               @RequestParam("status") String status,
                                               @RequestParam("tags") Integer[] tags) {
        Optional<Poster> poster = posterService.findPosterById(posterId);
        if(poster.isEmpty()) {
            logger.error("Poster Id: {} Not Exist in Database", posterId);
            throw new DataNotInDatabaseException("Poster Id Not Exist in Database");
        }
        catIdPresentAndValidate(thumbImage, bgImage, catId, isPurchase, status);
        try {
            Poster res = posterService.updateRecord(poster.get(), thumbImage, bgImage, isPurchase, height, width, status, catId);
            posterTagMappingService.deleteByPosterId(res.getPosterId());
            posterTagMappingService.insertNewRecord(res.getPosterId(), tags);
            logger.debug("Poster updated with id : {}", res.getPosterId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
