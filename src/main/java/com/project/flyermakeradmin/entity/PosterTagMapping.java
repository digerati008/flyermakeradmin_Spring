package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "poster_tag_mapping")
public class PosterTagMapping {

    @EmbeddedId
    private PosterTagMappingId id;

    public PosterTagMapping() {}

    public PosterTagMapping(PosterTagMappingId posterTagMappingId) {
        this.id = posterTagMappingId;
    }
}
