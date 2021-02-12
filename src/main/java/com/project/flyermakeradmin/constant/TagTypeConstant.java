package com.project.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public final class TagTypeConstant {
    public static final String TAG_POSTER="P";
    public static final String TAG_BACKGROUND = "B";
    public static final String TAG_GRAPHICS = "G";
    public static final String TAG_TEXTART = "T";
    public static final String TAG_SHAPE = "S";


    public static Set<String> getTagTypes() {
        Set<String> set = new HashSet<>();
        set.add(TAG_POSTER);
        set.add(TAG_BACKGROUND);
        set.add(TAG_GRAPHICS);
        set.add(TAG_TEXTART);
        set.add(TAG_SHAPE);
        return set;
    }

    private TagTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
