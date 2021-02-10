package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Integer> {

    @Query("select e from Shape e where e.shapeId in (:shapeIdSet)")
    List<Shape> findByBgId(Set<Integer> shapeIdSet);
}
