package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.Category;
import com.project.flyermakeradmin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    FileService fileService;

    @Autowired
    CategoryRepository categoryRepository;

    public Category insertNewRecord(String catType, String catName, MultipartFile imgFile) throws IOException {
        String fileDownloadUri =  fileService.storeFile(imgFile);

        Category category = new Category();
        category.setCatName(catName);
        category.setCatType(catType);
        category.setCatImgPath(fileDownloadUri);

        return categoryRepository.save(category);
    }

    public Category updateRecord(Category category, String catType, String catName, MultipartFile imgFile) throws IOException {
        String fileDownloadUri =  fileService.storeFile(imgFile);

        category.setCatName(catName);
        category.setCatType(catType);
        category.setCatImgPath(fileDownloadUri);

        return categoryRepository.save(category);
    }

    public boolean checkIfCatIdPresent(Integer catId, String catType) {
        return categoryRepository.existsByCatIdAndCatType(catId, catType);
    }

    public boolean checkIfCatIdPresent(Integer catId) {
        return categoryRepository.existsById(catId);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void deleteByCatId(Integer catId) {
        categoryRepository.deleteById(catId);
    }

    public Optional<Category> findByCatId(Integer catId) {
        return categoryRepository.findById(catId);
    }

}
