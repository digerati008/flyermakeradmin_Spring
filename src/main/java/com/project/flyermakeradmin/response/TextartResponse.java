package com.project.flyermakeradmin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TextartResponse {
    private Integer textartId;
    private String isPurchase;
    private String imgPath;
    private Integer catId;
    private String catName;
}

