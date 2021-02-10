package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.PosterTextMapping;
import com.dixit.flyermakeradmin.entity.PosterTextMappingId;
import com.dixit.flyermakeradmin.repository.PosterTextMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterTextMappingService {

    @Autowired
    PosterTextMappingRepository posterTextMappingRepository;

    public PosterTextMapping insertNewRecoed(Integer posterId, Integer textId) {
        PosterTextMappingId posterTextMappingId = new PosterTextMappingId(posterId, textId);
        PosterTextMapping posterTextMapping = new PosterTextMapping(posterTextMappingId);

        return posterTextMappingRepository.save(posterTextMapping);

    }

    public List<Integer> getTextIdsByPosterId(Integer posterId) {
        return posterTextMappingRepository.getTextIdsByPosterId(posterId);
    }

    public void deleteByPosterId(Integer posterId) {
        posterTextMappingRepository.deleteByPosterId(posterId);
    }

    public void deleteByTextId(Integer textId) {
        posterTextMappingRepository.deleteByTextId(textId);
    }
}
