package com.project.flyermakeradmin.service;


import com.project.flyermakeradmin.entity.Font;
import com.project.flyermakeradmin.repository.FontRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FontService {

    @Autowired
    FontRepository fontRepository;

    @Autowired
    FileService fileService;

    public Font insertNewRecord(String fontName, MultipartFile fontFile) throws IOException {
        String fileDownloadUri =  fileService.storeFile(fontFile);

        Font font = new Font();
        font.setFontName(fontName);
        font.setFontFilePath(fileDownloadUri);

        return fontRepository.save(font);
    }

    public Font updateRecord(Font font, String fontName, MultipartFile fontFile) throws IOException {
        String fileDownloadUri =  fileService.storeFile(fontFile);
        font.setFontName(fontName);
        font.setFontFilePath(fileDownloadUri);
        return fontRepository.save(font);
    }

    public boolean checkIfFontIdPresent(Integer fontId) {
        return fontRepository.existsById(fontId);
    }

    public List<Font> getAllFont() {
        return fontRepository.findAll();
    }

    public void deleteByFontId(Integer fontId) {
        fontRepository.deleteById(fontId);
    }

    public Optional<Font> findByFontId(Integer fontId) {
        return fontRepository.findById(fontId);
    }
}
