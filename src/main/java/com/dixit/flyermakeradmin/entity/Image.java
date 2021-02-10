package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Integer imgId;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "x_axis")
    private Integer xAxis;

    @Column(name = "y_axis")
    private Integer yAxis;

    @Column(name = "rotation")
    private Integer rotation;

    @Column(name = "z_index")
    private Integer zIndex;

    @Column(name = "opacity")
    private Integer opacity;

    @Column(name = "img_path")
    private String imgPath;
}