package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.entity.Text;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
import com.dixit.flyermakeradmin.service.FontService;
import com.dixit.flyermakeradmin.service.PosterService;
import com.dixit.flyermakeradmin.service.PosterTextMappingService;
import com.dixit.flyermakeradmin.service.TextService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/text")
public class TextController {
    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(TextController.class);

    @Autowired
    ValidateInputParam validateInputParam;

    @Autowired
    TextService textService;

    @Autowired
    PosterService posterService;

    @Autowired
    FontService fontService;

    @Autowired
    PosterTextMappingService posterTextMappingService;


    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertText(@RequestParam("posterId") Integer posterId,
                                             @RequestParam("fontId") Integer fontId,
                                             @RequestParam("height") Integer height,
                                             @RequestParam("width") Integer width,
                                             @RequestParam("value") String value,
                                             @RequestParam("xAxis") Integer xAxis,
                                             @RequestParam("yAxis") Integer yAxis,
                                             @RequestParam("fontSize") Integer fontSize,
                                             @RequestParam("color") String color,
                                             @RequestParam("rotation") Integer rotation,
                                             @RequestParam("align") String align,
                                             @RequestParam("opacity") Integer opacity,
                                             @RequestParam("zIndex") Integer zIndex,
                                             @RequestParam("lineSpacing") Integer lineSpacing,
                                             @RequestParam("letterSpacing") Integer letterSpacing
                                             ) {

        if(Boolean.FALSE.equals(posterService.checkIfPosterIdPresent(posterId))) {
            logger.error("Poster Id: {} Not Exist in Database", posterId);
            throw new DataNotInDatabaseException("Poster Id Not Exist in Database");
        }
        fontIdPresentAndValidate(fontId, value, color, align);
        try {
            Text res = textService.insertNewRecord(fontId, height, width, value, xAxis, yAxis, fontSize, color, rotation, align, opacity, zIndex, lineSpacing, letterSpacing);
            posterTextMappingService.insertNewRecoed(posterId, res.getTextId());
            logger.debug("New Text created with Text id : {} and Poster Id: {}", res.getTextId(), posterId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/delete/{textId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteText(@PathVariable Integer textId) {
        if(Boolean.FALSE.equals(textService.checkIfTextIdPresent(textId))) {
            logger.error("Text Id: {} Not Exist in Database", textId);
            throw new DataNotInDatabaseException("Text Id Not Exist in Database");
        }
        try {
            textService.deleteByTextId(textId);
            posterTextMappingService.deleteByTextId(textId);
            DeleteResponse res = new DeleteResponse("Text deleted with id : " + textId);
            logger.debug("Text deleted with id : {}", textId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateText(@RequestParam("textId") Integer textId,
                                             @RequestParam("fontId") Integer fontId,
                                             @RequestParam("height") Integer height,
                                             @RequestParam("width") Integer width,
                                             @RequestParam("value") String value,
                                             @RequestParam("xAxis") Integer xAxis,
                                             @RequestParam("yAxis") Integer yAxis,
                                             @RequestParam("fontSize") Integer fontSize,
                                             @RequestParam("color") String color,
                                             @RequestParam("rotation") Integer rotation,
                                             @RequestParam("align") String align,
                                             @RequestParam("opacity") Integer opacity,
                                             @RequestParam("zIndex") Integer zIndex,
                                             @RequestParam("lineSpacing") Integer lineSpacing,
                                             @RequestParam("letterSpacing") Integer letterSpacing) {

        Optional<Text> text = textService.findByTextId(textId);
        if(text.isEmpty()) {
            logger.error("Text Id: {} Not Exist in Database", textId);
            throw new DataNotInDatabaseException("Text Id Not Exist in Database");
        }
        fontIdPresentAndValidate(fontId, value, color, align);
        try {
            Text res = textService.updateRecord(text.get(), fontId, height, width, value, xAxis, yAxis, fontSize, color, rotation, align, opacity, zIndex, lineSpacing, letterSpacing);
            logger.debug("Text updated with Text id : {}", res.getTextId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void fontIdPresentAndValidate(Integer fontId, String value, String color, String align) {
        if(Boolean.FALSE.equals(fontService.checkIfFontIdPresent(fontId))) {
            logger.error("Font Id: {} Not Exist in Database", fontId);
            throw new DataNotInDatabaseException("Font Id Not Exist in Database");
        }
        if(Boolean.FALSE.equals(validateInputParam.textPost(value, color, align))) {
            logger.error("Input Parameter not valid for Text Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }
}
