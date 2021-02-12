package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "textart")
public class Textart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "textart_id")
    private Integer textartId;

    @Column(name = "is_purchase")
    private String isPurchase;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "cat_id")
    private Integer catId;
}