package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public class StatusTypeConstant {
    public static final String STATUS_ENABLE = "E";
    public static final String STATUS_DISABLE = "D";

    public static final Set<String> STATUS_TYPES = Set.of(STATUS_DISABLE, STATUS_ENABLE);

    private StatusTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
