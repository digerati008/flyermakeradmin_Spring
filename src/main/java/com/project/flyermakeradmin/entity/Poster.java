package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "poster")
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poster_id")
    private Integer posterId;

    @Column(name = "thumb_img_path")
    private String thumbImgPath;

    @Column(name = "is_purchase")
    private String isPurchase;

    @Column(name = "bg_img_path")
    private String bgImgPath;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "status")
    private String status;

    @Column(name = "cat_id")
    private Integer catId;
}