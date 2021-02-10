package com.dixit.flyermakeradmin.repository;

import com.dixit.flyermakeradmin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByCatIdAndCatType(Integer catId, String catType);
}
