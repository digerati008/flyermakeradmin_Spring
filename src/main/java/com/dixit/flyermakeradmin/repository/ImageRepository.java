package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from image as a  where a.img_id in (select b.img_id from poster_img_mapping as b where b.poster_id = :posterId)", nativeQuery = true)
    void deleteByPosterId(@Param("posterId") Integer posterId);
}
