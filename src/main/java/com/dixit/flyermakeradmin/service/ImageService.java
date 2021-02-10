package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.entity.Image;
import com.dixit.flyermakeradmin.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    FileService fileService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PosterImageMappingService posterImageMappingService;

    public Image insertNewRecord(MultipartFile imageFile, Integer height, Integer width, Integer xAxis, Integer yAxis, Integer rotation, Integer opacity, Integer zIndex) throws IOException {
        String fileDownloadUri = fileService.storeFile(imageFile);
        Image image = new Image();
        return getImage(height, width, xAxis, yAxis, rotation, opacity, zIndex, fileDownloadUri, image);
    }

    public Image updateRecord(Image image, MultipartFile imageFile, Integer height, Integer width, Integer xAxis, Integer yAxis, Integer rotation, Integer opacity, Integer zIndex) throws IOException {
        String fileDownloadUri = fileService.storeFile(imageFile);
        return getImage(height, width, xAxis, yAxis, rotation, opacity, zIndex, fileDownloadUri, image);
    }

    private Image getImage(Integer height, Integer width, Integer xAxis, Integer yAxis, Integer rotation, Integer opacity, Integer zIndex, String fileDownloadUri, Image image) {
        image.setHeight(height);
        image.setWidth(width);
        image.setXAxis(xAxis);
        image.setYAxis(yAxis);
        image.setRotation(rotation);
        image.setZIndex(zIndex);
        image.setOpacity(opacity);
        image.setImgPath(fileDownloadUri);

        return imageRepository.save(image);
    }

    public List<Image> getImageByPosterId(Integer posterId) {
        List<Integer> imageIdList = posterImageMappingService.getImageIdsByPosterId(posterId);
        if(!CollectionUtils.isEmpty(imageIdList))
            return imageRepository.findAllById(imageIdList);
        else
            return new ArrayList<>();
    }

    public void deleteByPosterId(Integer posterId) {
        imageRepository.deleteByPosterId(posterId);
    }

    public Optional<Image> findByImgId(Integer imgId) {
        return imageRepository.findById(imgId);
    }

    public Boolean checkIfImgIdPresent(Integer imgId) {
        return imageRepository.existsById(imgId);
    }

    public void deleteByImgId(Integer imgId) {
        imageRepository.deleteById(imgId);
    }
}
