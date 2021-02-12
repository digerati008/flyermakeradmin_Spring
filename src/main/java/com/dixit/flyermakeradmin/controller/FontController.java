package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.entity.Font;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
import com.dixit.flyermakeradmin.service.FontService;
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
@RequestMapping("/font")
public class FontController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(FontController.class);

    @Autowired
    FontService fontService;

    @Autowired
    ValidateInputParam validateInputParam;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFont(@RequestParam("fontName") String fontName,
                                             @RequestParam("fontFile") MultipartFile fontFile)  {

        validate(fontName, fontFile);
        try {
            Font res = fontService.insertNewRecord(fontName, fontFile);
            logger.debug("Font created with id : {}", res.getFontId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/delete/{fontId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFont(@PathVariable Integer fontId) {
        if(Boolean.FALSE.equals(fontService.checkIfFontIdPresent(fontId))) {
            logger.error("Font Id: {} Not Exist in Database", fontId);
            throw new DataNotInDatabaseException("Font Id Not Exist in Database");
        }
        try {
            fontService.deleteByFontId(fontId);
            DeleteResponse res = new DeleteResponse("font deleted with id : "+fontId);
            logger.debug("Font deleted with id : {}", fontId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateFont(@RequestParam("fontId") Integer fontId,
                                                 @RequestParam("fontName") String fontName,
                                                 @RequestParam("fontFile") MultipartFile fontFile) {
        Optional<Font> font = fontService.findByFontId(fontId);
        if(font.isEmpty()) {
            logger.error("Font Id: {} Not Exist in Database", fontId);
            throw new DataNotInDatabaseException("Font Id Not Exist in Database");
        }
        validate(fontName, fontFile);
        try {
            Font res = fontService.updateRecord(font.get(), fontName, fontFile);
            logger.debug("Font updated with id : {}", res.getFontId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validate(String fontName, MultipartFile fontFile) {
        if(Boolean.FALSE.equals(validateInputParam.fontPost(fontName, fontFile))) {
            logger.error("Input Parameter not valid for Font Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }
}

