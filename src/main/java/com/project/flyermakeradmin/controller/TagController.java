package com.project.flyermakeradmin.controller;

import com.project.flyermakeradmin.entity.Tag;
import com.project.flyermakeradmin.exception.DataNotInDatabaseException;
import com.project.flyermakeradmin.exception.NotValidInputException;
import com.project.flyermakeradmin.helper.ValidateInputParam;
import com.project.flyermakeradmin.response.DeleteResponse;
import com.project.flyermakeradmin.service.TagService;
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
@RequestMapping("/tag")
public class TagController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagService tagService;

    @Autowired
    ValidateInputParam validateInputParam;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTag(@RequestParam("tagType") String tagType,
                                            @RequestParam("tagName") String tagName)  {

        validate(tagType, tagName);
        try {
            Tag res = tagService.insertNewRecord(tagType, tagName);
            logger.debug("Tag created with id : {}", res.getTagId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/delete/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTag(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId))) {
            logger.error("Tag Id: {} Not Exist in Database", tagId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database");
        }
        try {
            tagService.deleteByTagId(tagId);
            DeleteResponse res = new DeleteResponse("tag deleted with id : "+tagId);
            logger.debug("Tag deleted with id : {}", tagId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTag(@RequestParam("tagId") Integer tagId,
                                                 @RequestParam("tagType") String tagType,
                                                 @RequestParam("tagName") String tagName) {
        Optional<Tag> tag = tagService.findByTagId(tagId);
        if(tag.isEmpty()) {
            logger.error("Tag Id: {} Not Exist in Database", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database");
        }
        validate(tagType, tagName);
        try {
            Tag res = tagService.updateRecord(tag.get(), tagType, tagName);
            logger.debug("Tag updated with id : {}", res.getTagId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validate(String tagType, String tagName) {
        if(Boolean.FALSE.equals(validateInputParam.tagPost(tagType, tagName))) {
            logger.error("Input Parameter not valid for Tag Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }
}
