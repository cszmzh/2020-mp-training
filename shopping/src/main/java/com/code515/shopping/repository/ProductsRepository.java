package com.code515.shopping.repository;

import com.code515.shopping.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

    /**
     * 根据类目查找所有商品
     */
    Page<Products> findAllByCateIdAndProdStatus(Integer cateId, Integer status, Pageable pageable);

    Page<Products> findAllByProdStatus(Integer prodStatus, Pageable pageable);

    /**
     * 根据关键字查找所有商品
     */
    List<Products> findAllByProdNameContainingAndProdStatusOrderByUpdateTimeDesc(String name, Integer status);

    /**
     * 通过发布者id查找所有商品
     */
    Page<Products> findAllBySubUserAndProdStatus(Integer subId, Integer prodStatus,Pageable pageable);

    Page<Products> findAllBySubUserAndProdStatusNotIn(Integer subId, List<Integer> collection, Pageable pageable);

    /**
     * 通过购买者id查找所有商品
     */
    Page<Products> findAllByBuyUser(Integer buyId,Pageable pageable);

    /**
     * 随机获取n条正在发布商品的数据
     */
    @Query(nativeQuery=true, value="select * from products where prod_status = 1 order by RAND() LIMIT ?1")
    List<Products> findRandomly(Integer n);
}

