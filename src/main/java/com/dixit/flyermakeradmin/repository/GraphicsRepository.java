package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Graphics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GraphicsRepository extends JpaRepository<Graphics, Integer> {

    @Query("select e from Graphics e where e.graphicsId in (:graphicsIdSet)")
    List<Graphics> findByGraphicsId(Set<Integer> graphicsIdSet);
}
