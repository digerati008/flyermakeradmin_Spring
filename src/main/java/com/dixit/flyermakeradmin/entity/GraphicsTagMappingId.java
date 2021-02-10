package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class GraphicsTagMappingId implements Serializable {

    @Column(name = "graphics_id")
    private Integer graphicsId;

    @Column(name = "tag_id")
    private Integer tagId;

    public GraphicsTagMappingId(Integer graphicsId, Integer tagId) {
        this.graphicsId = graphicsId;
        this.tagId = tagId;
    }

    public GraphicsTagMappingId() {}

}