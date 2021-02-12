package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "graphics")
public class Graphics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "graphics_id")
    private Integer graphicsId;

    @Column(name = "is_purchase")
    private String isPurchase;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "cat_id")
    private Integer catId;
}

