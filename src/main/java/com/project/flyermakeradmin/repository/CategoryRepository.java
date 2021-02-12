package com.project.flyermakeradmin.repository;

import com.project.flyermakeradmin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByCatIdAndCatType(Integer catId, String catType);
}
