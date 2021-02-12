package com.project.flyermakeradmin.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class PosterTextMappingId implements Serializable {

    @Column(name = "poster_id")
    private Integer posterId;

    @Column(name = "text_id")
    private Integer textId;

    public PosterTextMappingId(Integer posterId, Integer textId) {
        this.posterId = posterId;
        this.textId = textId;
    }

    public PosterTextMappingId() {}
}
