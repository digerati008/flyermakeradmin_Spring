package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "graphics_tag_mapping")
public class GraphicsTagMapping {

    @EmbeddedId
    private GraphicsTagMappingId id;

    public GraphicsTagMapping() {}

    public GraphicsTagMapping(GraphicsTagMappingId graphicsTagMappingId) {
        this.id = graphicsTagMappingId;
    }
}
