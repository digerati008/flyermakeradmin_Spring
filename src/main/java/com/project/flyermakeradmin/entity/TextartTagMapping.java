package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "textart_tag_mapping")
public class TextartTagMapping {

    @EmbeddedId
    private TextartTagMappingId id;

    public TextartTagMapping() {}

    public TextartTagMapping(TextartTagMappingId textartTagMappingId) {
        this.id = textartTagMappingId;
    }
}
