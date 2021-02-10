package com.dixit.flyermakeradmin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonResponse {
    private Integer id;
    private String isPurchase;
    private String imgPath;
    private Integer catId;
    private String catName;
}
