package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.*;
import com.project.flyermakeradmin.entity.Category;
import com.project.flyermakeradmin.entity.Tag;
import com.project.flyermakeradmin.entity.Textart;
import com.project.flyermakeradmin.entity.TextartTagMapping;
import com.project.flyermakeradmin.repository.TextartRepository;
import com.project.flyermakeradmin.response.CommonResponse;
import com.project.flyermakeradmin.response.TagResponse;
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

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

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
                Optional<Category> c = categoryService.findByCatId(t.getCatId());
                CommonResponse cr = new CommonResponse(t.getTextartId(), t.getIsPurchase(), t.getImgPath(), t.getCatId(), c.get().getCatName());
                Map<String, Object> map = new HashMap<>();
                map.put("textart", cr);
                List<TextartTagMapping> tagList = textartTagMappingService.getAllByTextartId(t.getTextartId());
                List<TagResponse> transformedTagList = getTransformedTagList(tagList);
                map.put("tags", transformedTagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }

    private List<TagResponse> getTransformedTagList(List<TextartTagMapping> tagList) {
        if (CollectionUtils.isEmpty(tagList))
            return new ArrayList<>();
        else {
            List<TagResponse> transformedTagList = new ArrayList<>();
            for(TextartTagMapping t: tagList) {
                Tag tag = tagService.getTagById(t.getId().getTagId());
                TagResponse tagResponse = new TagResponse(t.getId().getTagId(), tag.getTagName());
                transformedTagList.add(tagResponse);
            }
            return transformedTagList;
        }
    }
}
