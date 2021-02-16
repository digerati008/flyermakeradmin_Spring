package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.Shape;
import com.project.flyermakeradmin.entity.ShapeTagMapping;
import com.project.flyermakeradmin.entity.Tag;
import com.project.flyermakeradmin.repository.ShapeRepository;
import com.project.flyermakeradmin.response.ShapeResponse;
import com.project.flyermakeradmin.response.TagResponse;
import com.project.flyermakeradmin.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ShapeService {

    @Autowired
    FileService fileService;

    @Autowired
    ShapeRepository shapeRepository;

    @Autowired
    ShapeTagMappingService shapeTagMappingService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    public Shape insertNewRecord(MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        Shape shape = new Shape();
        shape.setImgPath(fileDownloadUri);
        shape.setCatId(catId);
        shape.setIsPurchase(isPurchase);

        return shapeRepository.save(shape);
    }

    public Shape updateRecord(Shape shape, MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        shape.setImgPath(fileDownloadUri);
        shape.setCatId(catId);
        shape.setIsPurchase(isPurchase);

        return shapeRepository.save(shape);
    }

    public Boolean checkIfShapeIdPresent(Integer shapeId) {
        return shapeRepository.existsById(shapeId);
    }

    public void deleteByShapeId(Integer shapeId) {
        shapeRepository.deleteById(shapeId);
    }

    public Optional<Shape> findByShapeId(Integer shapeId) {
        return shapeRepository.findById(shapeId);
    }

    public List<Shape> getShapeByTagId(Integer tagId) {
        List<Integer> shapeIdList = shapeTagMappingService.getShapeIdListByTagId(tagId);
        Set<Integer> shapeIdSet = new HashSet<>(shapeIdList);
        return shapeRepository.findByBgId(shapeIdSet);
    }

    public List<Map<String, Object>> getAllShapeDetails() {
        List<Shape> shapeList = shapeRepository.findAll();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(shapeList)) {
            for (Shape s: shapeList) {
                Optional<Category> c = categoryService.findByCatId(s.getCatId());
                ShapeResponse cr = new ShapeResponse(s.getShapeId(), s.getIsPurchase(), s.getImgPath(), s.getCatId(), c.get().getCatName());
                Map<String, Object> map = new HashMap<>();
                map.put("shape", cr);
                List<ShapeTagMapping> tagList = shapeTagMappingService.getAllByShapeId(s.getShapeId());
                List<TagResponse> transformedTagList = getTransformedTagList(tagList);
                map.put("tags", transformedTagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }

    private List<TagResponse> getTransformedTagList(List<ShapeTagMapping> tagList) {
        if (CollectionUtils.isEmpty(tagList))
            return new ArrayList<>();
        else {
            List<TagResponse> transformedTagList = new ArrayList<>();
            for(ShapeTagMapping t: tagList) {
                Tag tag = tagService.getTagById(t.getId().getTagId());
                TagResponse tagResponse = new TagResponse(t.getId().getTagId(), tag.getTagName());
                transformedTagList.add(tagResponse);
            }
            return transformedTagList;
        }
    }
}
