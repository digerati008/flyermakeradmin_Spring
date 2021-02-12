package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.BackgroundTagMapping;
import com.project.flyermakeradmin.entity.BackgroundTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BackgroundTagMappingRepository extends JpaRepository<BackgroundTagMapping, BackgroundTagMappingId> {

    @Transactional
    @Modifying
    @Query(value = "delete from background_tag_mapping as a where a.bg_id = :bgId", nativeQuery = true)
    void deleteByBgId(@Param("bgId") Integer bgId);

    @Query("select e.id.bgId from BackgroundTagMapping e where e.id.tagId = :tagId")
    List<Integer> getBackgroundIdListByTagId(Integer tagId);

    @Query("select e from BackgroundTagMapping e where e.id.bgId = :bgId")
    List<BackgroundTagMapping> findByBgId(Integer bgId);
}
