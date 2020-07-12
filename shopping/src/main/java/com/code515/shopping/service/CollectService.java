package com.code515.shopping.service;

import com.code515.shopping.entity.Collect;
import org.springframework.data.domain.Page;

public interface CollectService {

    /**
     * 1.通过用户id和商品id查找一条收藏
     * @param userId
     * @return
     */
    Collect getByUserIdAndProdId(Integer userId, Integer prodId);

    /**
     * 2.通过用户id获取全部收藏
     * @param userId
     * @return
     */
    Page<Collect> getAllByUserId(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 3.通过用户id和商品id删除收藏
     * @param userId
     */
    void deleteByUserIdAndProdId(Integer userId, Integer prodId);

    /**
     * 4.创建一条收藏
     * @param collect
     * @return
     */
    Collect createCollect(Collect collect);

    /**
     * 5.通过商品id计算收藏总数
     * @param prodId
     * @return
     */
    Integer countTotalByProdId(Integer prodId);

}
