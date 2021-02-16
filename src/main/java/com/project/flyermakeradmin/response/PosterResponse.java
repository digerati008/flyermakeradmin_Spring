package com.project.flyermakeradmin.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Data
public class PosterResponse {
    private Integer posterId;
    private String thumbImgPath;
    private String isPurchase;
    private String bgImgPath;
    private Integer height;
    private Integer width;
    private String status;
    private Integer catId;
    private String catName;
}
