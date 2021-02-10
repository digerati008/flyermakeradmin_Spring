package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public class FontFileTypeConstant {
    public static final String OTF = "application/octet-stream";
    public static final Set<String> FONT_FILE_TYPES = Set.of(OTF);

    private FontFileTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
