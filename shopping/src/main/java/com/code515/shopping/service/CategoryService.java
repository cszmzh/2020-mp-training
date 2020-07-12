package com.code515.shopping.service;

import com.code515.shopping.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 1.通过id获取分类信息
     * @param id
     * @return
     */
    public Category getByCategoryId(Integer id);

    /**
     * 2.获取全部分类信息
     * @return
     */
    public List<Category> getAll();

    public Category getByNameAndMininame(String name, String mininame);

}
