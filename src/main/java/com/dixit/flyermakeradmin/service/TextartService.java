package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.Textart;
import com.dixit.flyermakeradmin.entity.TextartTagMapping;
import com.dixit.flyermakeradmin.repository.TextartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class TextartService {

    @Autowired
    FileService fileService;

    @Autowired
    TextartRepository textartRepository;

    @Autowired
    TextartTagMappingService textartTagMappingService;

    public Textart insertNewRecord(MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        Textart textart = new Textart();
        textart.setImgPath(fileDownloadUri);
        textart.setCatId(catId);
        textart.setIsPurchase(isPurchase);

        return textartRepository.save(textart);
    }

    public Textart updateRecord(Textart textart, MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        textart.setImgPath(fileDownloadUri);
        textart.setCatId(catId);
        textart.setIsPurchase(isPurchase);

        return textartRepository.save(textart);
    }

    public Boolean checkIfTextartIdPresent(Integer textartId) {
        return textartRepository.existsById(textartId);
    }

    public void deleteByTextartId(Integer textartId) {
        textartRepository.deleteById(textartId);
    }

    public Optional<Textart> findByTextartId(Integer textartId) {
        return textartRepository.findById(textartId);
    }

    public List<Textart> getTextartByTagId(Integer tagId) {
        List<Integer> textartIdList = textartTagMappingService.getTextartIdListByTagId(tagId);
        Set<Integer> textartIdSet = new HashSet<>(textartIdList);
        return textartRepository.findByBgId(textartIdSet);
    }

    public List<Map<String, Object>> getAllTextartDetails() {
        List<Textart> textartList = textartRepository.findAll();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(textartList)) {
            for (Textart t: textartList) {
                Map<String, Object> map = new HashMap<>();
                map.put("textart", t);
                List<TextartTagMapping> tagList = textartTagMappingService.getAllByTextartId(t.getTextartId());
                map.put("tags", tagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }
}
