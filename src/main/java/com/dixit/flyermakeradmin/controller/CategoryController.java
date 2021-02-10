package com.dixit.flyermakeradmin.controller;

import com.dixit.flyermakeradmin.entity.Category;
import com.dixit.flyermakeradmin.exception.DataNotInDatabaseException;
import com.dixit.flyermakeradmin.exception.NotValidInputException;
import com.dixit.flyermakeradmin.helper.ValidateInputParam;
import com.dixit.flyermakeradmin.response.DeleteResponse;
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
@RequestMapping("/category")
public class CategoryController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    ValidateInputParam validateInputParam;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCategory(@RequestParam("catType") String catType,
                                @RequestParam("catName") String catName,
                                @RequestParam("catImage") MultipartFile imgFile) {
        validate(catType, catName, imgFile);
        try {
            Category res = categoryService.insertNewRecord(catType, catName, imgFile);
            logger.debug("Category created with id : {}", res.getCatId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validate(String catType, String catName, MultipartFile imgFile) {
        if(Boolean.FALSE.equals(validateInputParam.categoryPost(catType, catName, imgFile))) {
            logger.error("Input Parameter not valid for Category Input");
            throw new NotValidInputException("Input Parameter Not Valid");
        }
    }


    @PostMapping(value = "/delete/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCategory(@PathVariable Integer catId) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId))) {
            logger.error("Category Id: {} Not Exist in Database", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database");
        }
        try {
            categoryService.deleteByCatId(catId);
            DeleteResponse res = new DeleteResponse("category deleted with id : "+catId);
            logger.debug("Category deleted with id : {}", catId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCategory(@RequestParam("catId") Integer catId,
                                                 @RequestParam("catType") String catType,
                                                 @RequestParam("catName") String catName,
                                                 @RequestParam("catImage") MultipartFile imgFile) {
        Optional<Category> category = categoryService.findByCatId(catId);
        if(category.isEmpty()) {
            logger.error("Category Id: {} Not Exist in Database", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database");
        }
        validate(catType, catName, imgFile);
        try {
            Category res = categoryService.updateRecord(category.get(), catType, catName, imgFile);
            logger.debug("Category updated with id : {}", res.getCatId());
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}