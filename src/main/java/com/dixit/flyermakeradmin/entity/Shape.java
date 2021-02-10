package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shape")
public class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shape_id")
    private Integer shapeId;

    @Column(name = "is_purchase")
    private String isPurchase;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "cat_id")
    private Integer catId;
}