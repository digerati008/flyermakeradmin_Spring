package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.BackgroundTagMapping;
import com.project.flyermakeradmin.entity.BackgroundTagMappingId;
import com.project.flyermakeradmin.repository.BackgroundTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackgroundTagMappingService {

    @Autowired
    BackgroundTagMappingRepository backgroundTagMappingRepository;

    public List<BackgroundTagMapping> insertNewRecord(Integer bgId, Integer[] tags) {
        List<BackgroundTagMapping> backgroundTagMappingList = new ArrayList<>();
        for(Integer i : tags) {
            BackgroundTagMappingId backgroundTagMappingId = new BackgroundTagMappingId(bgId, i);
            backgroundTagMappingList.add(new BackgroundTagMapping(backgroundTagMappingId));
        }
        return backgroundTagMappingRepository.saveAll(backgroundTagMappingList);
    }

    public void deleteByBgId(Integer bgId) {
        backgroundTagMappingRepository.deleteByBgId(bgId);
    }

    public List<Integer> getBackgroundIdListByTagId(Integer tagId) {
        return backgroundTagMappingRepository.getBackgroundIdListByTagId(tagId);
    }

    public List<BackgroundTagMapping> getAllByBgId(Integer bgId) {
        return backgroundTagMappingRepository.findByBgId(bgId);
    }
}
