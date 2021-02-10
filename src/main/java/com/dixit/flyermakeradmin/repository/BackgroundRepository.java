package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Integer> {

    @Query("select e from Background e where e.bgId in (:bgIdSet)")
    List<Background> findByBgId(Set<Integer> bgIdSet);
}
