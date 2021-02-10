package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class PosterTagMappingId implements Serializable {

    @Column(name = "poster_id")
    private Integer posterId;

    @Column(name = "tag_id")
    private Integer tagId;

    public PosterTagMappingId(Integer posterId, Integer tagId) {
        this.posterId = posterId;
        this.tagId = tagId;
    }

    public PosterTagMappingId() {}
}
