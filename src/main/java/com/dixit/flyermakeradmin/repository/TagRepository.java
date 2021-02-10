package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByTagIdAndTagType(Integer tagId, String tagType);
}
