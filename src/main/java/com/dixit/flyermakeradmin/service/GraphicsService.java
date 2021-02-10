package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.Graphics;
import com.dixit.flyermakeradmin.entity.GraphicsTagMapping;
import com.dixit.flyermakeradmin.repository.GraphicsRepository;
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
                Map<String, Object> map = new HashMap<>();
                map.put("graphics", g);
                List<GraphicsTagMapping> tagList = graphicsTagMappingService.getAllByGraphicsId(g.getGraphicsId());
                map.put("tags", tagList);
                mapList.add(map);
            }
            return mapList;
        } else
            return new ArrayList<>();
    }
}
