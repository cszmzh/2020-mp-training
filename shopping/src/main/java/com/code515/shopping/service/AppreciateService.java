package com.code515.shopping.service;

import com.code515.shopping.entity.Appreciate;

import java.util.List;

public interface AppreciateService {

    /**
     * 1.通过用户id和商品id查找一条点赞
     * @param userId
     * @return
     */
    Appreciate getByUserIdAndProdId(Integer userId, Integer prodId);

    /**
     * 2.通过用户id获取全部收藏
     * @param userId
     * @return
     */
    List<Appreciate> getAllByUserId(Integer userId);

    /**
     * 3.通过用户id和商品id删除点赞
     * @param userId
     */
    void deleteByUserIdAndProdId(Integer userId, Integer prodId);

    /**
     * 4.创建一条收藏
     * @param
     * @return
     */
    Appreciate createAppreciate(Appreciate appreciate);

    /**
     * 5.通过商品id计算收藏总数
     * @param prodId
     * @return
     */
    Integer countTotalByProdId(Integer prodId);

}
