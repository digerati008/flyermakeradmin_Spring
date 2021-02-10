package com.dixit.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public final class CategoryTypeConstant {
    public static final String CAT_POSTER = "P";
    public static final String CAT_BACKGROUND = "B";
    public static final String CAT_GRAPHICS = "G";
    public static final String CAT_TEXTART = "T";
    public static final String CAT_SHAPE = "S";

    public static Set<String> getCatTypes() {
        Set<String> set = new HashSet<>();
        set.add(CAT_SHAPE);
        set.add(CAT_BACKGROUND);
        set.add(CAT_POSTER);
        set.add(CAT_GRAPHICS);
        set.add(CAT_TEXTART);
        return set;
    }

    private CategoryTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
