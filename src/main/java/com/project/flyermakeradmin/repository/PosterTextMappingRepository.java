package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.PosterTextMapping;
import com.project.flyermakeradmin.entity.PosterTextMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PosterTextMappingRepository extends JpaRepository<PosterTextMapping, PosterTextMappingId> {
    @Query("select e.id.textId from PosterTextMapping e where e.id.posterId = :posterId")
    List<Integer> getTextIdsByPosterId(Integer posterId);

    @Transactional
    @Modifying
    @Query(value = "delete from poster_text_mapping as a where a.poster_id = :posterId", nativeQuery = true)
    void deleteByPosterId(@Param("posterId") Integer posterId);

    @Transactional
    @Modifying
    @Query(value = "delete from poster_text_mapping as a where a.text_id = :textId", nativeQuery = true)
    void deleteByTextId(@Param("textId") Integer textId);
}
