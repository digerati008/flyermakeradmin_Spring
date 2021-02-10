package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public final class TagTypeConstant {
    public static final String TAG_POSTER="P";
    public static final String TAG_BACKGROUND = "B";
    public static final String TAG_GRAPHICS = "G";
    public static final String TAG_TEXTART = "T";
    public static final String TAG_SHAPE = "S";

    public static final Set<String> TAG_TYPES = Set.of(TAG_POSTER, TAG_BACKGROUND, TAG_GRAPHICS, TAG_TEXTART, TAG_SHAPE);

    private TagTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
