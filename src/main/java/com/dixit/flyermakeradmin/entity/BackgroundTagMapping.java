package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "background_tag_mapping")
public class BackgroundTagMapping {

    @EmbeddedId
    private BackgroundTagMappingId id;

    public BackgroundTagMapping() {}

    public BackgroundTagMapping(BackgroundTagMappingId backgroundTagMappingId) {
        this.id = backgroundTagMappingId;
    }
}
