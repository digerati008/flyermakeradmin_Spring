package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.GraphicsTagMapping;
import com.project.flyermakeradmin.entity.GraphicsTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GraphicsTagMappingRepository extends JpaRepository<GraphicsTagMapping, GraphicsTagMappingId> {

    @Transactional
    @Modifying
    @Query(value = "delete from graphics_tag_mapping as a where a.graphics_id = :graphicsId", nativeQuery = true)
    void deleteByGraphicsId(@Param("graphicsId") Integer graphicsId);

    @Query("select e.id.graphicsId from GraphicsTagMapping e where e.id.tagId = :tagId")
    List<Integer> getGraphicsIdListByTagId(Integer tagId);

    @Query("select e from GraphicsTagMapping e where e.id.graphicsId = :graphicsId")
    List<GraphicsTagMapping> findByGraphicsId(Integer graphicsId);
}
