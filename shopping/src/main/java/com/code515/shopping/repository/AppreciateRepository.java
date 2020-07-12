package com.code515.shopping.repository;

import com.code515.shopping.entity.Appreciate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppreciateRepository extends JpaRepository<Appreciate, Integer> {

    Appreciate findByUserIdAndProdId(Integer userId, Integer prodId);

    List<Appreciate> findAllByUserIdOrderByCreateTimeDesc(Integer id);

    @Transactional
    void deleteByUserIdAndProdId(Integer userId,Integer prodId);

    Integer countAllByProdId(Integer prodId);

}
