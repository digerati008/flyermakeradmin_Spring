package com.project.flyermakeradmin.controller;

import com.project.flyermakeradmin.constant.CategoryTypeConstant;
import com.project.flyermakeradmin.constant.TagTypeConstant;
import com.project.flyermakeradmin.entity.*;
import com.project.flyermakeradmin.entity.*;
import com.project.flyermakeradmin.exception.DataNotInDatabaseException;
import com.project.flyermakeradmin.response.PosterIdThumbPurchase;
import com.project.flyermakeradmin.service.*;
import com.google.gson.Gson;
import com.project.flyermakeradmin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@Controller
public class MainController {

    public static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    FileService fileService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    @Autowired
    FontService fontService;

    @Autowired
    PosterService posterService;

    @Autowired
    ImageService imageService;

    @Autowired
    TextService textService;

    @Autowired
    BackgroundService backgroundService;

    @Autowired
    GraphicsService graphicsService;

    @Autowired
    TextartService textartService;

    @Autowired
    ShapeService shapeService;


    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileService.getImageAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.debug("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/categoryTagFont", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCategoryTagFont() {
        try{
            Map<String, Object> res = new HashMap<>();
            List<Category> categoryList = categoryService.getAllCategory();
            List<Font> fontList = fontService.getAllFont();
            List<Tag> tagList = tagService.getAllTag();

            res.put("category", categoryList);
            res.put("tag", tagList);
            res.put("font", fontList);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/poster/{posterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPosterDetailById(@PathVariable Integer posterId) {

        Optional<Poster> poster = posterService.findPosterById(posterId);
        if(poster.isEmpty()) {
            logger.error("Poster Id: {} Not Exist in Database", posterId);
            throw new DataNotInDatabaseException("Poster Id Not Exist in Database");
        }
        try {
            Map<String, Object> res = new HashMap<>();

            List<Image> imageList = imageService.getImageByPosterId(posterId);
            List<Text> textList = textService.getTextByPosterId(posterId);

            res.put("poster", poster);
            res.put("image", imageList);
            res.put("text", textList);
            logger.debug("Poster returned with id : {}", posterId);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getPosterDetailById fetching poster details : {}", posterId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/posterIdThumbPurchaseByCatId/{catId}/{pageNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPosterIdThumbPurchasedByCatId(@PathVariable Integer catId,
                                                      @PathVariable Integer pageNo) {
        if(Boolean.FALSE.equals(categoryService.checkIfCatIdPresent(catId, CategoryTypeConstant.CAT_POSTER))) {
            logger.error("Category Id: {} Not Exist in Database for Type = poster", catId);
            throw new DataNotInDatabaseException("Category Id Not Exist in Database for type = poster");
        }
        try {
            Map<String, Object> res = new HashMap<>();
            List<PosterIdThumbPurchase> posterIdThumbPurchaseList = posterService.getPosterIdThumbPurchaseByCatId(catId, pageNo);
            Integer totalSize = posterService.getTotalSizeByCatId(catId);
            res.put("size", totalSize);
            res.put("list", posterIdThumbPurchaseList);
            logger.debug("Poster Id and Thumb and isPurchase List returned for catId : {} and pageNo: {}",  catId, pageNo);
            return new ResponseEntity<>(gson.toJson(res), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getPosterIdThumbPurchase fetching posterId and Thumb and isPurchase details for catId: {} and PageNo: {} ", catId, pageNo);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/posterIdThumbPurchaseByTagId/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPosterIdThumbPurchaseByTagId(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId, TagTypeConstant.TAG_POSTER))) {
            logger.error("Tag Id: {} Not Exist in Database for Type = poster", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database for type = poster");
        }
        try {
            List<PosterIdThumbPurchase> posterIdThumbPurchaseList = posterService.getPosterIdThumbPurchaseByTagId(tagId);
            logger.debug("Poster Id and Thumb and isPurchase List returned for tagId : {}",  tagId);
            return new ResponseEntity<>(gson.toJson(posterIdThumbPurchaseList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getPosterThumbAndId fetching posterId and Thumb details for tagId: {} ", tagId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/backgroundByTagId/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBackgroundByTagId(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId, TagTypeConstant.TAG_BACKGROUND))) {
            logger.error("Tag Id: {} Not Exist in Database for Type = Background", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database for type = background");
        }
        try {
            List<Background> backgroundList = backgroundService.getBackgroundByTagId(tagId);
            logger.debug("Background List returned for tagId : {}",  tagId);
            return new ResponseEntity<>(gson.toJson(backgroundList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getBackgroundByTagId fetching background list for tagId: {} ", tagId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/graphicsByTagId/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGraphicsByTagId(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId, TagTypeConstant.TAG_GRAPHICS))) {
            logger.error("Tag Id: {} Not Exist in Database for Type = Graphics", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database for type = graphics");
        }
        try {
            List<Graphics> graphicsList = graphicsService.getGraphicsByTagId(tagId);
            logger.debug("Graphics List returned for tagId : {}",  tagId);
            return new ResponseEntity<>(gson.toJson(graphicsList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getGraphicsByTagId fetching graphics list for tagId: {} ", tagId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/textartByTagId/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTextartByTagId(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId, TagTypeConstant.TAG_TEXTART))) {
            logger.error("Tag Id: {} Not Exist in Database for Type = Textart", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database for type = textart");
        }
        try {
            List<Textart> textartList = textartService.getTextartByTagId(tagId);
            logger.debug("Textart List returned for tagId : {}",  tagId);
            return new ResponseEntity<>(gson.toJson(textartList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getTextartByTagId fetching Textart list for tagId: {} ", tagId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/shapeByTagId/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getShapeByTagId(@PathVariable Integer tagId) {
        if(Boolean.FALSE.equals(tagService.checkIfTagIdPresent(tagId, TagTypeConstant.TAG_SHAPE))) {
            logger.error("Tag Id: {} Not Exist in Database for Type = Shape", tagId);
            throw new DataNotInDatabaseException("Tag Id Not Exist in Database for type = shape");
        }
        try {
            List<Shape> shapeList = shapeService.getShapeByTagId(tagId);
            logger.debug("Shape List returned for tagId : {}",  tagId);
            return new ResponseEntity<>(gson.toJson(shapeList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getShapeByTagId fetching Shape list for tagId: {} ", tagId);
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/allBackgroundDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllBackgroundDetails() {
        try {
            List<Map<String, Object>> backgroundList = backgroundService.getAllBackgroundDetails();
            logger.debug("All Background Details List returned");
            return new ResponseEntity<>(gson.toJson(backgroundList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getAllBackgroundDetails");
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/allGraphicsDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllGraphicsDetails() {
        try {
            List<Map<String, Object>> graphicsList = graphicsService.getAllGraphicsDetails();
            logger.debug("All Graphics Details List returned");
            return new ResponseEntity<>(gson.toJson(graphicsList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getAllGraphicsDetails");
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/allTextartDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllTextartDetails() {
        try {
            List<Map<String, Object>> textartList = textartService.getAllTextartDetails();
            logger.debug("All Textart Details List returned");
            return new ResponseEntity<>(gson.toJson(textartList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getAllTextartDetails");
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/allShapeDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllShapeDetails() {
        try {
            List<Map<String, Object>> shapeList = shapeService.getAllShapeDetails();
            logger.debug("All Shape Details List returned");
            return new ResponseEntity<>(gson.toJson(shapeList), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getAllShapeDetails");
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
