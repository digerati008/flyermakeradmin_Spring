package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class BackgroundTagMappingId implements Serializable {

    @Column(name = "bg_id")
    private Integer bgId;

    @Column(name = "tag_id")
    private Integer tagId;

    public BackgroundTagMappingId(Integer bgId, Integer tagId) {
        this.bgId = bgId;
        this.tagId = tagId;
    }

    public BackgroundTagMappingId() {}

}
