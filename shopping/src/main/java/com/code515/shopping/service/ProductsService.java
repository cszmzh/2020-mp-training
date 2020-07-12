package com.code515.shopping.service;

import com.code515.shopping.entity.Products;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {

    /**
     * 1.获取所有商品(根据状态查询)
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<Products> getAllByProdStatus(Integer prodStatus,Integer pageNum, Integer pageSize);

    /**
     * 2.通过商品ID获取
     * @param id
     * @return
     */
    public Products getOneById(Integer id);

    /**
     * 2.根据分类id获取所有商品
     */
    public Page<Products> getAllByCategoryId(Integer cateId,Integer pageNum, Integer pageSize);

    /**
     * 3.根据发布用户id和商品状态查所有商品
     * @param subId
     * @return
     */
    public Page<Products> getAllBySubIdAndProdStatus(Integer subId, Integer status, Integer pageNum, Integer pageSize);
    public Page<Products> getAllBySubIdAndProdStatusNotIn(Integer subId, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 4.根据购买用户id查所有商品
     * @param buyId
     * @return
     */
    public Page<Products> getAllByBuyId(Integer buyId, Integer pageNum, Integer pageSize);

    /**
     * 5.根据商品Id删除
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 6.根据id添加商品信息
     * @param products
     */
    public void createProduct(Products products);

    /**
     * 7.更新商品信息
     * @param products
     */
    public void updateProduct(Products products);

    /**
     * 8.随机获取商品
     */
    public List<Products> getProductsRandomly(Integer num);

    /**
     * 9.根据关键字查找商品
     */
    public List<Products> getProductsByNameLike(String prodName);
}
