package com.project.flyermakeradmin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShapeResponse {
    private Integer shapeId;
    private String isPurchase;
    private String imgPath;
    private Integer catId;
    private String catName;
}

