package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.Category;
import com.code515.shopping.repository.CategoryRepository;
import com.code515.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category getByCategoryId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getByNameAndMininame(String name, String mininame) {
        return repository.findByNameAndMininame(name,mininame);
    }

}
