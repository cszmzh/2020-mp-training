package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.Products;
import com.code515.shopping.repository.ProductsRepository;
import com.code515.shopping.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository repository;




    @Override
    public Page<Products> getAllByProdStatus(Integer prodStatus, Integer pageNum, Integer pageSize) {
        //根据时间降序
        Sort sort = Sort.by(Sort.Order.desc("updateTime"));
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Products> page = repository.findAllByProdStatus(prodStatus,pageable);
        return page;
    }

    @Override
    public Products getOneById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<Products> getAllByCategoryId(Integer cateId,Integer pageNum, Integer pageSize) {
        //根据时间降序
        Sort sort = Sort.by(Sort.Order.desc("updateTime"));
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Products> page = repository.findAllByCateIdAndProdStatus(cateId,1,pageable);
        return page;
    }

    @Override
    public Page<Products> getAllBySubIdAndProdStatus(Integer subId, Integer status,Integer pageNum, Integer pageSize) {
        //根据商品创建时间降序
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        return repository.findAllBySubUserAndProdStatus(subId, status, pageable);
    }

    @Override
    public Page<Products> getAllBySubIdAndProdStatusNotIn(Integer subId, Integer status, Integer pageNum, Integer pageSize) {
        //根据商品创建时间降序
        Sort sort = Sort.by(Sort.Order.desc("buyTime"));
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(status);
        return repository.findAllBySubUserAndProdStatusNotIn(subId, statusList, pageable);
    }

    @Override
    public Page<Products> getAllByBuyId(Integer buyId, Integer pageNum, Integer pageSize) {
        //根据购买时间降序
        Sort sort = Sort.by(Sort.Order.desc("buyTime"));
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        return repository.findAllByBuyUser(buyId,pageable);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void createProduct(Products products) {
        repository.save(products);
    }

    @Override
    public void updateProduct(Products products) {
        Products productsTemp = getOneById(products.getProdId());

        productsTemp.setProdName(products.getProdName());
        productsTemp.setNowPrice(products.getNowPrice());
        productsTemp.setPrePrice(products.getPrePrice());
        productsTemp.setCateId(products.getCateId());
        productsTemp.setSubUser(products.getSubUser());
        productsTemp.setBuyUser(products.getBuyUser());
        productsTemp.setProdStatus(products.getProdStatus());
        productsTemp.setProdDescription(products.getProdDescription());
        productsTemp.setSubContact(products.getSubContact());
        productsTemp.setBuyContact(products.getBuyContact());
        productsTemp.setProdImg1(products.getProdImg1());
        productsTemp.setProdImg2(products.getProdImg2());
        productsTemp.setProdImg3(products.getProdImg3());
        productsTemp.setProdImg4(products.getProdImg4());
        productsTemp.setBuyTime(products.getBuyTime());

        repository.save(productsTemp);
    }

    @Override
    public List<Products> getProductsRandomly(Integer num) {
        return repository.findRandomly(num);
    }

    @Override
    public List<Products> getProductsByNameLike(String prodName) {
        //根据关键字查询已发布商品
        return repository.findAllByProdNameContainingAndProdStatusOrderByUpdateTimeDesc(prodName,1);
    }
}
