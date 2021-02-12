package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.ShapeTagMapping;
import com.project.flyermakeradmin.entity.ShapeTagMappingId;
import com.project.flyermakeradmin.repository.ShapeTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShapeTagMappingService {

    @Autowired
    ShapeTagMappingRepository shapeTagMappingRepository;

    public List<ShapeTagMapping> insertNewRecord(Integer shapeId, Integer[] tags) {
        List<ShapeTagMapping> shapeTagMappingList = new ArrayList<>();
        for(Integer i : tags) {
            ShapeTagMappingId shapeTagMappingId = new ShapeTagMappingId(shapeId, i);
            shapeTagMappingList.add(new ShapeTagMapping(shapeTagMappingId));
        }
        return shapeTagMappingRepository.saveAll(shapeTagMappingList);
    }

    public void deleteByShapeId(Integer shapeId) {
        shapeTagMappingRepository.deleteByShapeId(shapeId);
    }

    public List<Integer> getShapeIdListByTagId(Integer tagId) {
        return shapeTagMappingRepository.getShapeIdListByTagId(tagId);
    }

    public List<ShapeTagMapping> getAllByShapeId(Integer shapeId) {
        return shapeTagMappingRepository.getAllByShapeId(shapeId);
    }
}
