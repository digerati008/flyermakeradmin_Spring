package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public final class CategoryTypeConstant {
    public static final String CAT_POSTER = "P";
    public static final String CAT_BACKGROUND = "B";
    public static final String CAT_GRAPHICS = "G";
    public static final String CAT_TEXTART = "T";
    public static final String CAT_SHAPE = "S";

    public static final Set<String> CAT_TYPES = Set.of(CAT_POSTER, CAT_BACKGROUND, CAT_GRAPHICS, CAT_TEXTART, CAT_SHAPE);

    private CategoryTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
