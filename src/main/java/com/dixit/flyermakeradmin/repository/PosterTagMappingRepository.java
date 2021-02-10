package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.PosterTagMapping;
import com.dixit.flyermakeradmin.entity.PosterTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PosterTagMappingRepository extends JpaRepository<PosterTagMapping, PosterTagMappingId> {

    @Query("select e.id.posterId from PosterTagMapping e where e.id.tagId = :tagId")
    List<Integer> getPosterIdListByTagId(Integer tagId);

    @Transactional
    @Modifying
    @Query(value = "delete from poster_tag_mapping as a where a.poster_id = :posterId", nativeQuery = true)
    void deleteByPosterId(@Param("posterId") Integer posterId);
}
