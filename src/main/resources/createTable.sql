show tables;

create table category(
                         cat_id int not null auto_increment,
                         cat_type varchar(1),
                         cat_name varchar(40),
                         cat_img_path varchar(1000),
                         primary key (cat_id)
);

create table tag(
                    tag_id int not null auto_increment,
                    tag_type varchar(1),
                    tag_name varchar(40),
                    primary key (tag_id)
);

create table font(
                     font_id int not null auto_increment,
                     font_name varchar(40),
                     font_file_path varchar(1000),
                     primary key (font_id)
);

create table text(
                     text_id int not null auto_increment,
                     font_id int not null,
                     height int,
                     width int,
                     value varchar(1000),
                     x_axis int,
                     y_axis int,
                     font_size int,
                     color varchar(40),
                     rotation int,
                     align varchar(10),
                     z_index int,
                     opacity int,
                     line_spacing int,
                     letter_spacing int,
                     primary key (text_id)
);

create table image(
                      img_id int not null auto_increment,
                      height int,
                      width int,
                      x_axis int,
                      y_axis int,
                      rotation int,
                      z_index int,
                      opacity int,
                      img_path varchar(1000),
                      primary key (img_id)
);

create table poster(
                       poster_id int not null auto_increment,
                       thumb_img_path varchar(1000),
                       is_purchase varchar(1),
                       bg_img_path varchar(1000),
                       height int,
                       width int,
                       status varchar(1),
                       cat_id int not null,
                       primary key (poster_id)
);

create table poster_tag_mapping(
                                   poster_id int not null,
                                   tag_id int not null,
                                   primary key (poster_id, tag_id)
);

create table poster_img_mapping(
                                   poster_id int not null,
                                   img_id int not null,
                                   primary key (poster_id, img_id)
);

create table poster_text_mapping(
                                    poster_id int not null,
                                    text_id int not null,
                                    primary key (poster_id, text_id)
);

create table background (
    bg_id int not null auto_increment,
    is_purchase varchar(1),
    img_path varchar(1000),
    cat_id int not null,
    primary key (bg_id)
);

create table background_tag_mapping (
    bg_id int not null,
    tag_id int not null,
    primary key (bg_id, tag_id)
);

create table graphics (
                            graphics_id int not null auto_increment,
                            is_purchase varchar(1),
                            img_path varchar(1000),
                            cat_id int not null,
                            primary key (graphics_id)
);

create table graphics_tag_mapping (
                                        graphics_id int not null,
                                        tag_id int not null,
                                        primary key (graphics_id, tag_id)
);

create table textart (
                            textart_id int not null auto_increment,
                            is_purchase varchar(1),
                            img_path varchar(1000),
                            cat_id int not null,
                            primary key (textart_id)
);

create table textart_tag_mapping (
                                        textart_id int not null,
                                        tag_id int not null,
                                        primary key (textart_id, tag_id)
);

create table shape (
                            shape_id int not null auto_increment,
                            is_purchase varchar(1),
                            img_path varchar(1000),
                            cat_id int not null,
                            primary key (shape_id)
);

create table shape_tag_mapping (
                                        shape_id int not null,
                                        tag_id int not null,
                                        primary key (shape_id, tag_id)
);


