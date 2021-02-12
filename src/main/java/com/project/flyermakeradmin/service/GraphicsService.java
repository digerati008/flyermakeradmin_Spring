package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.*;
import com.project.flyermakeradmin.entity.Graphics;
import com.project.flyermakeradmin.entity.GraphicsTagMapping;
import com.project.flyermakeradmin.entity.Tag;
import com.project.flyermakeradmin.repository.GraphicsRepository;
import com.project.flyermakeradmin.response.CommonResponse;
import com.project.flyermakeradmin.response.TagResponse;
import com.project.flyermakeradmin.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class GraphicsService {

    @Autowired
    FileService fileService;

    @Autowired
    GraphicsRepository graphicsRepository;

    @Autowired
    GraphicsTagMappingService graphicsTagMappingService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    public Graphics insertNewRecord(MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        Graphics graphics = new Graphics();
        graphics.setImgPath(fileDownloadUri);
        graphics.setCatId(catId);
        graphics.setIsPurchase(isPurchase);

        return graphicsRepository.save(graphics);
    }

    public Graphics updateRecord(Graphics graphics, MultipartFile image, Integer catId, String isPurchase) throws IOException {
        String fileDownloadUri = fileService.storeFile(image);

        graphics.setImgPath(fileDownloadUri);
        graphics.setCatId(catId);
        graphics.setIsPurchase(isPurchase);

        return graphicsRepository.save(graphics);
    }

    public Boolean checkIfGraphicsIdPresent(Integer graphicsId) {
        return graphicsRepository.existsById(graphicsId);
    }

    public void deleteByGraphicsId(Integer graphicsId) {
        graphicsRepository.deleteById(graphicsId);
    }

    public Optional<Graphics> findByGraphicsId(Integer graphicsId) {
        return graphicsRepository.findById(graphicsId);
    }

    public List<Graphics> getGraphicsByTagId(Integer tagId) {
        List<Integer> graphicsIdList = graphicsTagMappingService.getGraphicsIdListByTagId(tagId);
        Set<Integer> graphicsIdSet = new HashSet<>(graphicsIdList);
        return graphicsRepository.findByGraphicsId(graphicsIdSet);
    }

    public List<Map<String, Object>> getAllGraphicsDetails() {
        List<Graphics> graphicsList = graphicsRepository.findAll();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(graphicsList)) {
            for (Graphics g: graphicsList) {
                Optional<Category> c = categoryService.findByCatId(g.getCatId());
                CommonResponse cr = new CommonResponse(g.getGraphicsId(), g.getIsPurchase(), g.getImgPath(), g.getCatId(), c.get().getCatName());
                Map<String, Object> map = new HashMap<>();
                map.put("textart", cr);
                List<GraphicsTagMapping> tagList = graphicsTagMappingService.getAllByGraphicsId(g.getGraphicsId());
                List<TagResponse> transformedTagList = getTransformedTagList(tagList);
                map.put("tags", transformedTagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }

    private List<TagResponse> getTransformedTagList(List<GraphicsTagMapping> tagList) {
        if (CollectionUtils.isEmpty(tagList))
            return new ArrayList<>();
        else {
            List<TagResponse> transformedTagList = new ArrayList<>();
            for(GraphicsTagMapping t: tagList) {
                Tag tag = tagService.getTagById(t.getId().getTagId());
                TagResponse tagResponse = new TagResponse(t.getId().getTagId(), tag.getTagName());
                transformedTagList.add(tagResponse);
            }
            return transformedTagList;
        }
    }
}
