package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from text as a where a.text_id in (select b.text_id from poster_text_mapping as b where b.poster_id = :posterId)", nativeQuery = true)
    void deleteByPosterId(@Param("posterId") Integer posterId);
}
