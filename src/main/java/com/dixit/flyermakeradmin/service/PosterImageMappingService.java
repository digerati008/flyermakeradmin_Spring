package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.PosterImageMapping;
import com.dixit.flyermakeradmin.entity.PosterImageMappingId;
import com.dixit.flyermakeradmin.repository.PosterImageMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterImageMappingService {
    @Autowired
    PosterImageMappingRepository posterImageMappingRepository;


    public PosterImageMapping insertNewRecoed(Integer posterId, Integer imgId) {
        PosterImageMappingId posterImageMappingId = new PosterImageMappingId(posterId, imgId);
        PosterImageMapping posterImageMapping = new PosterImageMapping(posterImageMappingId);

        return posterImageMappingRepository.save(posterImageMapping);
    }

    public List<Integer> getImageIdsByPosterId(Integer posterId) {
        return posterImageMappingRepository.getImageIdsByPosterId(posterId);
    }

    public void deleteByPosterId(Integer posterId) {
        posterImageMappingRepository.deleteByPosterId(posterId);
    }

    public void deleteByImgId(Integer imgId) {
        posterImageMappingRepository.deleteByImgId(imgId);
    }
}
