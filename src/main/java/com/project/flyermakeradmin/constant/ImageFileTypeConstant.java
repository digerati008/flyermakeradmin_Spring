package com.project.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public class ImageFileTypeConstant {
    public static final String JPEG = "image/jpeg";
    public static final String JPG = "image/jpg";
    public static final String PNG = "image/png";
    public static final String SVG = "image/svg+xml";

    public static Set<String> getImageFileTypes() {
        Set<String> set = new HashSet<>();
        set.add(JPEG);
        set.add(JPG);
        set.add(PNG);
        set.add(SVG);
        return set;
    }

    private ImageFileTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
