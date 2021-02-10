package com.dixit.flyermakeradmin.service;

import com.dixit.flyermakeradmin.constant.FileStorageConstant;
import com.dixit.flyermakeradmin.controller.CategoryController;
import com.dixit.flyermakeradmin.exception.MyFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class FileService {

    public String storeFile(MultipartFile file) throws IOException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
        String fileName = strDate + file.getOriginalFilename() ;
        fileName = StringUtils.cleanPath(Objects.requireNonNull(fileName));
        if (fileName.contains("..")) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Path fileStorageLocation = Paths.get(FileStorageConstant.FILE_LOCATION).toAbsolutePath().normalize().resolve(fileName);
        Files.copy(file.getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(FileStorageConstant.FILE_URL)
                .path(fileName)
                .toUriString();
    }

    public Resource getImageAsResource(String fileName) {
        try {
            Path fileStorageLocation = Paths.get(FileStorageConstant.FILE_LOCATION).toAbsolutePath().normalize().resolve(fileName);
            Resource resource = new UrlResource(fileStorageLocation.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
