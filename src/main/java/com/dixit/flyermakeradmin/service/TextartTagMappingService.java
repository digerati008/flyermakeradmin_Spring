package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.TextartTagMapping;
import com.dixit.flyermakeradmin.entity.TextartTagMappingId;
import com.dixit.flyermakeradmin.repository.TextartTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextartTagMappingService {

    @Autowired
    TextartTagMappingRepository textartTagMappingRepository;

    public List<TextartTagMapping> insertNewRecord(Integer textartId, Integer[] tags) {
        List<TextartTagMapping> textartTagMappingList = new ArrayList<>();
        for(Integer i : tags) {
            TextartTagMappingId textartTagMappingId = new TextartTagMappingId(textartId, i);
            textartTagMappingList.add(new TextartTagMapping(textartTagMappingId));
        }
        return textartTagMappingRepository.saveAll(textartTagMappingList);
    }

    public void deleteByTextartId(Integer textartId) {
        textartTagMappingRepository.deleteByTextartId(textartId);
    }

    public List<Integer> getTextartIdListByTagId(Integer tagId) {
        return textartTagMappingRepository.getTextartIdListByTagId(tagId);
    }

    public List<TextartTagMapping> getAllByTextartId(Integer textartId) {
        return textartTagMappingRepository.findByTextartId(textartId);
    }
}
