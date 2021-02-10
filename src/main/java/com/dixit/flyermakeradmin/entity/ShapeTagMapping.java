package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shape_tag_mapping")
public class ShapeTagMapping {

    @EmbeddedId
    private ShapeTagMappingId id;

    public ShapeTagMapping() {}

    public ShapeTagMapping(ShapeTagMappingId shapeTagMappingId) {
        this.id = shapeTagMappingId;
    }
}