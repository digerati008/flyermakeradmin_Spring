package com.project.flyermakeradmin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BackgroundResponse {
    private Integer bgId;
    private String isPurchase;
    private String imgPath;
    private Integer catId;
    private String catName;
}
