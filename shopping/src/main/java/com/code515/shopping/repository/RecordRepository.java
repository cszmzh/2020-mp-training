package com.code515.shopping.repository;

import com.code515.shopping.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Integer> {

    /**
     * 通过用户id和商品id查找记录，时间降序
     */
    List<Record> findAllByUserIdAndProdIdOrderByCreateTimeDesc(Integer userId, Integer productId);

}
