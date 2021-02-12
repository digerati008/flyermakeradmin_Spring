package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "background")
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bg_id")
    private Integer bgId;

    @Column(name = "is_purchase")
    private String isPurchase;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "cat_id")
    private Integer catId;
}
