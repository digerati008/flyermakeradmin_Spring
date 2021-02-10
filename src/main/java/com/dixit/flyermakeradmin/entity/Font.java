package com.dixit.flyermakeradmin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "font")
public class Font {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "font_id")
    private Integer fontId;

    @Column(name = "font_name")
    private String fontName;

    @Column(name = "font_file_path")
    private String fontFilePath;
}
