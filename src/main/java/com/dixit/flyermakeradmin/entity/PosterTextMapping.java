package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "poster_text_mapping")
public class PosterTextMapping {

    @EmbeddedId
    private PosterTextMappingId id;

    public PosterTextMapping() {}

    public PosterTextMapping(PosterTextMappingId posterTextMappingId) {
        this.id = posterTextMappingId;
    }
}
