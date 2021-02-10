package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class PosterImageMappingId implements Serializable {

    @Column(name = "poster_id")
    private Integer posterId;

    @Column(name = "img_id")
    private Integer imgId;

    public PosterImageMappingId(Integer posterId, Integer imgId) {
        this.posterId = posterId;
        this.imgId = imgId;
    }

    public PosterImageMappingId() {}
}
