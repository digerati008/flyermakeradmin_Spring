package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Font;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FontRepository extends JpaRepository<Font, Integer> {
}
