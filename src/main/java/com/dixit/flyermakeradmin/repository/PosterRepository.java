package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Poster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Integer> {

    @Query("select count(e) from Poster e where e.catId = :catId")
    Integer getTotalSizeByCatId(Integer catId);

    List<Poster> findAllByCatId(Integer catId, Pageable page);

    @Query("select e from Poster e where e.posterId in (:posterIdSet)")
    List<Poster> findByPosterId(Set<Integer> posterIdSet);
}
