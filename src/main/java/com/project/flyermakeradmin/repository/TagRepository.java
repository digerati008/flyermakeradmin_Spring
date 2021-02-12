package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByTagIdAndTagType(Integer tagId, String tagType);

    Tag findByTagId(Integer tagId);
}
