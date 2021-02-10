package com.dixit.flyermakeradmin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosterIdThumbPurchase {
    private Integer posterId;
    private String thumbImgPath;
    private String isPurchase;
}
