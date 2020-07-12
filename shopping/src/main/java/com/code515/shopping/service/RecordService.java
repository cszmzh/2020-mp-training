package com.code515.shopping.service;

import com.code515.shopping.entity.Record;

import java.util.List;

public interface RecordService {

    /**
     * 1.通过用户id和商品id查找记录
     * @param userId
     * @param productId
     * @return
     */
    List<Record> getAllByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * 2.新增一条记录
     */
    Record createOne(Record record);
}
