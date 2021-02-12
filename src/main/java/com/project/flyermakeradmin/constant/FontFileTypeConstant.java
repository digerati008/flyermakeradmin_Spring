package com.project.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public class FontFileTypeConstant {
    public static final String OTF = "application/octet-stream";
    public static Set<String> getFontFileTypes() {
        Set<String> set = new HashSet<>();
        set.add(OTF);
        return set;
    }

    private FontFileTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
