package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.GraphicsTagMapping;
import com.dixit.flyermakeradmin.entity.GraphicsTagMappingId;
import com.dixit.flyermakeradmin.repository.GraphicsTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphicsTagMappingService {

    @Autowired
    GraphicsTagMappingRepository graphicsTagMappingRepository;

    public List<GraphicsTagMapping> insertNewRecord(Integer graphicsId, Integer[] tags) {
        List<GraphicsTagMapping> graphicsTagMappingList = new ArrayList<>();
        for(Integer i : tags) {
            GraphicsTagMappingId graphicsTagMappingId = new GraphicsTagMappingId(graphicsId, i);
            graphicsTagMappingList.add(new GraphicsTagMapping(graphicsTagMappingId));
        }
        return graphicsTagMappingRepository.saveAll(graphicsTagMappingList);
    }

    public void deleteByGraphicsId(Integer graphicsId) {
        graphicsTagMappingRepository.deleteByGraphicsId(graphicsId);
    }

    public List<Integer> getGraphicsIdListByTagId(Integer tagId) {
        return graphicsTagMappingRepository.getGraphicsIdListByTagId(tagId);
    }

    public List<GraphicsTagMapping> getAllByGraphicsId(Integer graphicsId) {
        return graphicsTagMappingRepository.findByGraphicsId(graphicsId);
    }
}
