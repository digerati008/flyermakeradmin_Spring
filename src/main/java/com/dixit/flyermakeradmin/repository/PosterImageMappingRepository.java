package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.PosterImageMapping;
import com.dixit.flyermakeradmin.entity.PosterImageMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PosterImageMappingRepository extends JpaRepository<PosterImageMapping, PosterImageMappingId> {
    @Query("select e.id.imgId from PosterImageMapping e where e.id.posterId = :posterId")
    List<Integer> getImageIdsByPosterId(Integer posterId);

    @Transactional
    @Modifying
    @Query(value = "delete from poster_img_mapping as a where a.poster_id = :posterId", nativeQuery = true)
    void deleteByPosterId(@Param("posterId") Integer posterId);

    @Transactional
    @Modifying
    @Query(value = "delete from poster_img_mapping as a where a.img_id = :imgId", nativeQuery = true)
    void deleteByImgId(@Param("imgId") Integer imgId);
}
