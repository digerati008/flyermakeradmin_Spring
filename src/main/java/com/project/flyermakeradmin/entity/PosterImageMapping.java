package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "poster_img_mapping")
public class PosterImageMapping {

    @EmbeddedId
    private PosterImageMappingId id;

    public PosterImageMapping() {}

    public PosterImageMapping(PosterImageMappingId posterImageMappingId) {
        this.id = posterImageMappingId;
    }
}
