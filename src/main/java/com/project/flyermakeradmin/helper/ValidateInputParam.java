package com.project.flyermakeradmin.helper;

import com.project.flyermakeradmin.constant.*;
import com.project.flyermakeradmin.constant.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ValidateInputParam {
    private Boolean checkString1(String in) {
        return in != null && in.trim().length() == 1;
    }
    private Boolean checkString(String in) {
        return in != null && in.trim().length() != 0;
    }
    private Boolean checkFile(MultipartFile in) {
        return in != null;
    }

    private Boolean checkImgFileType(String type) {
        return ImageFileTypeConstant.getImageFileTypes().contains(type);
    }

    private Boolean checkFontFileType(String type) {
        return FontFileTypeConstant.getFontFileTypes().contains(type);
    }

    private Boolean checkCatTypeString(String in) {
        return CategoryTypeConstant.getCatTypes().contains(in);
    }

    private Boolean checkTagTypeString(String in) {
        return TagTypeConstant.getTagTypes().contains(in);
    }

    private Boolean checkStatusTypeString(String in) {
        return StatusTypeConstant.getStatusTypes().contains(in);
    }

    private Boolean checkIsPurchaseTypeString(String in) {
        return IsPurchaseTypeConstant.getIsPurchaseTypes().contains(in);
    }


    public Boolean categoryPost(String catType, String catName, MultipartFile imgFile) {
        return checkString1(catType) && checkCatTypeString(catType) && checkString(catName) && checkFile(imgFile) && checkImgFileType(imgFile.getContentType());
    }

    public boolean tagPost(String tagType, String tagName) {
        return checkString1(tagType) && checkTagTypeString(tagType) && checkString(tagName);
    }

    public boolean fontPost(String fontName, MultipartFile fontFile) {
        return checkString(fontName) && checkFile(fontFile) && checkFontFileType(fontFile.getContentType()) ;
    }


    public boolean posterPost(MultipartFile thumbImage, MultipartFile bgImage, String isPurchase, String status) {
        return checkFile(thumbImage) && checkFile(bgImage) &&
                checkImgFileType(thumbImage.getContentType()) && checkImgFileType(bgImage.getContentType()) &&
                checkString1(isPurchase) && checkString1(status) &&
                checkIsPurchaseTypeString(isPurchase) && checkStatusTypeString(status);
    }

    public boolean imagePost(MultipartFile image) {
        return checkFile(image) && checkImgFileType(image.getContentType());
    }

    public boolean textPost(String value, String color, String align) {
        return checkString(value) && checkString(color) && checkString(align);
    }

    public boolean backgroundPost(MultipartFile image, String isPurchase) {
        return checkFile(image) && checkImgFileType(image.getContentType()) && checkString1(isPurchase) && checkIsPurchaseTypeString(isPurchase);
    }

    public boolean graphicsPost(MultipartFile image, String isPurchase) {
        return checkFile(image) && checkImgFileType(image.getContentType()) && checkString1(isPurchase) && checkIsPurchaseTypeString(isPurchase);
    }

    public boolean textartPost(MultipartFile image, String isPurchase) {
        return checkFile(image) && checkImgFileType(image.getContentType()) && checkString1(isPurchase) && checkIsPurchaseTypeString(isPurchase);
    }

    public boolean shapePost(MultipartFile image, String isPurchase) {
        return checkFile(image) && checkImgFileType(image.getContentType()) && checkString1(isPurchase) && checkIsPurchaseTypeString(isPurchase);
    }
}
