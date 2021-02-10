package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class TextartTagMappingId implements Serializable {

    @Column(name = "textart_id")
    private Integer textartId;

    @Column(name = "tag_id")
    private Integer tagId;

    public TextartTagMappingId(Integer textartId, Integer tagId) {
        this.textartId = textartId;
        this.tagId = tagId;
    }

    public TextartTagMappingId() {}

}