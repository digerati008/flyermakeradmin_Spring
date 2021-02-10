package com.dixit.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public class StatusTypeConstant {
    public static final String STATUS_ENABLE = "E";
    public static final String STATUS_DISABLE = "D";

    public static Set<String> getStatusTypes() {
        Set<String> set = new HashSet<>();
        set.add(STATUS_DISABLE);
        set.add(STATUS_ENABLE);
        return set;
    }

    private StatusTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
