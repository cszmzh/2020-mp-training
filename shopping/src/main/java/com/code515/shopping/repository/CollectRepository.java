package com.code515.shopping.repository;

import com.code515.shopping.entity.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CollectRepository extends JpaRepository<Collect, Integer> {

    Collect findByUserIdAndProdId(Integer userId, Integer prodId);

    Page<Collect> findAllByUserIdOrderByCreateTimeDesc(Integer id, Pageable pageable);

    @Transactional
    void deleteByUserIdAndProdId(Integer userId,Integer prodId);

    Integer countAllByProdId(Integer prodId);
}
