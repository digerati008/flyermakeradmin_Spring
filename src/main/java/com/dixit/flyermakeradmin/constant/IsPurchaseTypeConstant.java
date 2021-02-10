package com.dixit.flyermakeradmin.constant;

import java.util.HashSet;
import java.util.Set;

public class IsPurchaseTypeConstant {
    public static final String PURCHASE_YES = "Y";
    public static final String PURCHASE_NO = "N";

    public static Set<String> getIsPurchaseTypes() {
        Set<String> set = new HashSet<>();
        set.add(PURCHASE_NO);
        set.add(PURCHASE_YES);
        return set;
    }

    private IsPurchaseTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
