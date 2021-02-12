package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.ShapeTagMapping;
import com.project.flyermakeradmin.entity.ShapeTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShapeTagMappingRepository extends JpaRepository<ShapeTagMapping, ShapeTagMappingId> {

    @Transactional
    @Modifying
    @Query(value = "delete from shape_tag_mapping as a where a.shape_id = :shapeId", nativeQuery = true)
    void deleteByShapeId(@Param("shapeId") Integer shapeId);

    @Query("select e.id.shapeId from ShapeTagMapping e where e.id.tagId = :tagId")
    List<Integer> getShapeIdListByTagId(Integer tagId);

    @Query("select e from ShapeTagMapping e where e.id.shapeId = :shapeId")
    List<ShapeTagMapping> getAllByShapeId(Integer shapeId);
}
