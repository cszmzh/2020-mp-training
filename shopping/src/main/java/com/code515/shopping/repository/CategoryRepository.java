package com.code515.shopping.repository;

import com.code515.shopping.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByNameOrderByIdAsc(String name);

    Category findByNameAndMininame(String name,String mininame);

}
