package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.Background;
import com.project.flyermakeradmin.entity.BackgroundTagMapping;
import com.project.flyermakeradmin.entity.Category;
import com.project.flyermakeradmin.entity.Poster;
import com.project.flyermakeradmin.repository.PosterRepository;
import com.project.flyermakeradmin.response.PosterIdThumbPurchase;
import com.project.flyermakeradmin.response.PosterResponse;
import com.project.flyermakeradmin.response.TagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PosterService {

    @Autowired
    FileService fileService;

    @Autowired
    PosterRepository posterRepository;

    @Autowired
    PosterTagMappingService posterTagMappingService;

    @Autowired
    CategoryService categoryService;

    public Poster insertNewRecord(MultipartFile thumbImage, MultipartFile bgImage, String isPurchase, Integer height, Integer width, String status, Integer catId) throws IOException {
        String fileDownloadUriThumb = fileService.storeFile(thumbImage);
        String fileDownloadUriBg = fileService.storeFile(bgImage);
        Poster poster = new Poster();
        return getPoster(isPurchase, height, width, status, catId, fileDownloadUriThumb, fileDownloadUriBg, poster);
    }

    public Poster updateRecord(Poster poster, MultipartFile thumbImage, MultipartFile bgImage, String isPurchase, Integer height, Integer width, String status, Integer catId) throws IOException {
        String fileDownloadUriThumb = fileService.storeFile(thumbImage);
        String fileDownloadUriBg = fileService.storeFile(bgImage);
        return getPoster(isPurchase, height, width, status, catId, fileDownloadUriThumb, fileDownloadUriBg, poster);
    }

    private Poster getPoster(String isPurchase, Integer height, Integer width, String status, Integer catId, String fileDownloadUriThumb, String fileDownloadUriBg, Poster poster) {
        poster.setThumbImgPath(fileDownloadUriThumb);
        poster.setIsPurchase(isPurchase);
        poster.setBgImgPath(fileDownloadUriBg);
        poster.setHeight(height);
        poster.setWidth(width);
        poster.setStatus(status);
        poster.setCatId(catId);

        return posterRepository.save(poster);
    }

    public List<PosterResponse> getAllPosterDetails() {
        List<Poster> posterList = posterRepository.findAll();
        List<PosterResponse> resList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(posterList)) {
            for (Poster b: posterList) {
                Optional<Category> c = categoryService.findByCatId(b.getCatId());
                PosterResponse cr = new PosterResponse(b.getPosterId(),b.getThumbImgPath(), b.getIsPurchase(), b.getBgImgPath(),b.getHeight(),b.getWidth(),b.getStatus(),b.getCatId(), c.get().getCatName());
                resList.add(cr);
            }

            return resList;
        } else
            return new ArrayList<>();
    }

    public boolean checkIfPosterIdPresent(Integer posterId) {
        return posterRepository.existsById(posterId);
    }

    public Optional<Poster> findPosterById(Integer posterId) {
        return posterRepository.findById(posterId);
    }

    public List<PosterIdThumbPurchase> getPosterIdThumbPurchaseByCatId(Integer catId, Integer pageNo) {
        Pageable page = PageRequest.of(pageNo-1, 1, Sort.by("posterId").descending());
        List<Poster> posterList = posterRepository.findAllByCatId(catId, page);
        return getPosterIdThumbPurchaseList(posterList);
    }

    public List<PosterIdThumbPurchase> getPosterIdThumbPurchaseByTagId(Integer tagId) {
        List<Integer> posterIdList = posterTagMappingService.getPosterIdListByTagId(tagId);
        Set<Integer> posterIdSet = new HashSet<>(posterIdList);
        List<Poster> posterList = posterRepository.findByPosterId(posterIdSet);
        return getPosterIdThumbPurchaseList(posterList);
    }

    private List<PosterIdThumbPurchase> getPosterIdThumbPurchaseList(List<Poster> posterList) {
        List<PosterIdThumbPurchase> posterIdThumbPurchaseList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(posterList)) {
            for(Poster p: posterList) {
                PosterIdThumbPurchase pidThumbPurchase = new PosterIdThumbPurchase();
                pidThumbPurchase.setPosterId(p.getPosterId());
                pidThumbPurchase.setThumbImgPath(p.getThumbImgPath());
                pidThumbPurchase.setIsPurchase(p.getIsPurchase());
                posterIdThumbPurchaseList.add(pidThumbPurchase);
            }
            return posterIdThumbPurchaseList;
        } else
            return new ArrayList<>();
    }

    public Integer getTotalSizeByCatId(Integer catId) {
        return posterRepository.getTotalSizeByCatId(catId);
    }

    public void deleteByPosterId(Integer posterId) {
        posterRepository.deleteById(posterId);
    }
}
