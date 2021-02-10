package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.Shape;
import com.dixit.flyermakeradmin.entity.ShapeTagMapping;
import com.dixit.flyermakeradmin.repository.ShapeRepository;
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
                Map<String, Object> map = new HashMap<>();
                map.put("shape", s);
                List<ShapeTagMapping> tagList = shapeTagMappingService.getAllByShapeId(s.getShapeId());
                map.put("tags", tagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }
}
