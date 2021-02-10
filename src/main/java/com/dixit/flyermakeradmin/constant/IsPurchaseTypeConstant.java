package com.dixit.flyermakeradmin.constant;

import java.util.Set;

public class IsPurchaseTypeConstant {
    public static final String PURCHASE_YES = "Y";
    public static final String PURCHASE_NO = "N";

    public static final Set<String> IS_PURCHASE_TYPES = Set.of(PURCHASE_NO, PURCHASE_YES);

    private IsPurchaseTypeConstant() {
        throw new IllegalStateException("Utility class");
    }
}
