package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.Background;
import com.dixit.flyermakeradmin.entity.BackgroundTagMapping;
import com.dixit.flyermakeradmin.repository.BackgroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BackgroundService {

    @Autowired
    FileService fileService;

    @Autowired
    BackgroundRepository backgroundRepository;

    @Autowired
    BackgroundTagMappingService backgroundTagMappingService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    public Background insertNewRecord(MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        Background background = new Background();
        background.setImgPath(fileDownloadUri);
        background.setCatId(catId);
        background.setIsPurchase(isPurchase);

        return backgroundRepository.save(background);
    }

    public Background updateRecord(Background background, MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        background.setImgPath(fileDownloadUri);
        background.setCatId(catId);
        background.setIsPurchase(isPurchase);

        return backgroundRepository.save(background);
    }

    public Boolean checkIfBgIdPresent(Integer bgId) {
        return backgroundRepository.existsById(bgId);
    }

    public void deleteByBgId(Integer bgId) {
        backgroundRepository.deleteById(bgId);
    }

    public Optional<Background> findByBgId(Integer bgId) {
        return backgroundRepository.findById(bgId);
    }

    public List<Background> getBackgroundByTagId(Integer tagId) {
        List<Integer> backgroundIdList = backgroundTagMappingService.getBackgroundIdListByTagId(tagId);
        Set<Integer> bgIdSet = new HashSet<>(backgroundIdList);
        return backgroundRepository.findByBgId(bgIdSet);

    }

    public List<Map<String, Object>> getAllBackgroundDetails() {
        List<Background> backgroundList = backgroundRepository.findAll();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(backgroundList)) {
            for (Background b: backgroundList) {
                Map<String, Object> map = new HashMap<>();
                map.put("background", b);
                List<BackgroundTagMapping> tagList = backgroundTagMappingService.getAllByBgId(b.getBgId());
                map.put("tags", tagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }
}
