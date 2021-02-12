package com.project.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer catId;

    @Column(name = "cat_type")
    private String catType;

    @Column(name = "cat_name")
    private String catName;

    @Column(name = "cat_img_path")
    private String catImgPath;
}
