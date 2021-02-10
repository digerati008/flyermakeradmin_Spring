package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Textart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TextartRepository extends JpaRepository<Textart, Integer> {

    @Query("select e from Textart e where e.textartId in (:textartIdSet)")
    List<Textart> findByBgId(Set<Integer> textartIdSet);
}
