package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "text")
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_id")
    private Integer textId;

    @Column(name = "font_id")
    private Integer fontId;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "value")
    private String value;

    @Column(name = "x_axis")
    private Integer xAxis;

    @Column(name = "y_axis")
    private Integer yAxis;

    @Column(name = "font_size")
    private Integer fontSize;

    @Column(name = "color")
    private String color;

    @Column(name = "rotation")
    private Integer rotation;

    @Column(name = "align")
    private String align;

    @Column(name = "z_index")
    private Integer zIndex;

    @Column(name = "opacity")
    private Integer opacity;

    @Column(name = "line_spacing")
    private Integer lineSpacing;

    @Column(name = "letter_spacing")
    private Integer letterSpacing;
}