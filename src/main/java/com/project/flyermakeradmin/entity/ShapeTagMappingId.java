package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ShapeTagMappingId implements Serializable {

    @Column(name = "shape_id")
    private Integer shapeId;

    @Column(name = "tag_id")
    private Integer tagId;

    public ShapeTagMappingId(Integer shapeId, Integer tagId) {
        this.shapeId = shapeId;
        this.tagId = tagId;
    }

    public ShapeTagMappingId() {}

}
