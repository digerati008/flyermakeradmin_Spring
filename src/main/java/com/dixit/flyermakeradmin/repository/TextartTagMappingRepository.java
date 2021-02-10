package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.TextartTagMapping;
import com.dixit.flyermakeradmin.entity.TextartTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TextartTagMappingRepository extends JpaRepository<TextartTagMapping, TextartTagMappingId> {

    @Transactional
    @Modifying
    @Query(value = "delete from textart_tag_mapping as a where a.textart_id = :textartId", nativeQuery = true)
    void deleteByTextartId(@Param("textartId") Integer textartId);

    @Query("select e.id.textartId from TextartTagMapping e where e.id.tagId = :tagId")
    List<Integer> getTextartIdListByTagId(Integer tagId);

    @Query("select e from TextartTagMapping e where e.id.textartId = :textartId")
    List<TextartTagMapping> findByTextartId(Integer textartId);
}
