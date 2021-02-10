package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public class ImageFileTypeConstant {
    public static final String JPEG = "image/jpeg";
    public static final String JPG = "image/jpg";
    public static final String PNG = "image/png";
    public static final String SVG = "image/svg+xml";

    public static final Set<String> IMAGE_FILE_TYPES = Set.of(JPEG, JPG, PNG, SVG);

    private ImageFileTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
