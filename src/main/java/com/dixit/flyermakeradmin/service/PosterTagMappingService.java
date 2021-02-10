package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.PosterTagMapping;
import com.dixit.flyermakeradmin.entity.PosterTagMappingId;
import com.dixit.flyermakeradmin.repository.PosterTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosterTagMappingService {
    @Autowired
    PosterTagMappingRepository posterTagMappingRepository;

    public List<PosterTagMapping> insertNewRecord(Integer posterId, Integer[] tags) {
        List<PosterTagMapping> posterTagMappingList = new ArrayList<>();
        for(Integer i : tags) {
            PosterTagMappingId posterTagMappingId = new PosterTagMappingId(posterId, i);
            posterTagMappingList.add(new PosterTagMapping(posterTagMappingId));
        }
        return posterTagMappingRepository.saveAll(posterTagMappingList);
    }

    public List<Integer> getPosterIdListByTagId(Integer tagId) {
        return posterTagMappingRepository.getPosterIdListByTagId(tagId);
    }

    public void deleteByPosterId(Integer posterId) {
        posterTagMappingRepository.deleteByPosterId(posterId);
    }
}
