package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.entity.Image;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
import com.dixit.flyermakeradmin.service.ImageService;
import com.dixit.flyermakeradmin.service.PosterImageMappingService;
import com.dixit.flyermakeradmin.service.PosterService;
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
@RequestMapping("/image")
public class ImageController {
    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    ImageService imageService;

    @Autowired
    PosterService posterService;

    @Autowired
    PosterImageMappingService posterImageMappingService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertImage(@RequestParam("posterId") Integer posterId,
                                               @RequestParam("image") MultipartFile imageFile,
                                               @RequestParam("height") Integer height,
                                               @RequestParam("width") Integer width,
                                               @RequestParam("xAxis") Integer xAxis,
                                               @RequestParam("yAxis") Integer yAxis,
                                               @RequestParam("rotation") Integer rotation,
                                               @RequestParam("opacity") Integer opacity,
                                               @RequestParam("zIndex") Integer zIndex) {

        if(Boolean.FALSE.equals(posterService.checkIfPosterIdPresent(posterId))) {
            logger.error("Poster Id: {} Not Exist in Database", posterId);
            throw new DataNotInDatabaseException("Poster Id Not Exist in Database");
        }
        validate(imageFile);
        try {
            Image res = imageService.insertNewRecord(imageFile, height, width, xAxis, yAxis, rotation, opacity, zIndex);
            posterImageMappingService.insertNewRecoed(posterId, res.getImgId());
            logger.debug("New Image created with Image id : {} and Poster Id: {}", res.getImgId(), posterId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validate(MultipartFile imageFile) {
        if(Boolean.FALSE.equals(validateInputParam.imagePost(imageFile))) {
            logger.error("Input Parameter not valid for Image Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }

    @PostMapping(value = "/delete/{imgId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteImage(@PathVariable Integer imgId) {
        if(Boolean.FALSE.equals(imageService.checkIfImgIdPresent(imgId))) {
            logger.error("Image Id: {} Not Exist in Database", imgId);
            throw new DataNotInDatabaseException("Image Id Not Exist in Database");
        }
        try {
            imageService.deleteByImgId(imgId);
            posterImageMappingService.deleteByImgId(imgId);
            DeleteResponse res = new DeleteResponse("Image deleted with id : " + imgId);
            logger.debug("Image deleted with id : {}", imgId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateImage(@RequestParam("imgId") Integer imgId,
                                              @RequestParam("image") MultipartFile imageFile,
                                              @RequestParam("height") Integer height,
                                              @RequestParam("width") Integer width,
                                              @RequestParam("xAxis") Integer xAxis,
                                              @RequestParam("yAxis") Integer yAxis,
                                              @RequestParam("rotation") Integer rotation,
                                              @RequestParam("opacity") Integer opacity,
                                              @RequestParam("zIndex") Integer zIndex) {

        Optional<Image> image = imageService.findByImgId(imgId);
        if(image.isEmpty()) {
            logger.error("Image Id: {} Not Exist in Database", imgId);
            throw new DataNotInDatabaseException("Image Id Not Exist in Database");
        }
        validate(imageFile);
        try {
            Image res = imageService.updateRecord(image.get(), imageFile, height, width, xAxis, yAxis, rotation, opacity, zIndex);
            logger.debug("Image updated with Image id : {}", res.getImgId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
