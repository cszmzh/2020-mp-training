package com.code515.shopping.controller;

import com.code515.shopping.VO.CollectVO.CollectDataVO;
import com.code515.shopping.VO.CollectVO.CollectVO;
import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.Appreciate;
import com.code515.shopping.entity.Collect;
import com.code515.shopping.entity.Products;
import com.code515.shopping.entity.User;
import com.code515.shopping.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/click")
public class CollectAndLikeController {

    @Autowired
    private WxService wxService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppreciateService appreciateService;

    @Autowired
    private ProductsService productsService;

    /**
     * 1.处理收藏事件
     */
    @PostMapping("/collect")
    public ResultVO clickCollect(@RequestParam("code")String code, @RequestParam("productId")Integer productId) throws IOException {

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        if(collectService.getByUserIdAndProdId(user.getUserId(),productId)==null){
            Collect collect = new Collect();
            collect.setUserId(user.getUserId());
            collect.setProdId(productId);
            collectService.createCollect(collect);
        }else{
            collectService.deleteByUserIdAndProdId(user.getUserId(),productId);
        }

        return new ResultVO(0,"success");
    }

    /**
     * 2.处理点赞事件
     * @return
     */
    @PostMapping("/like")
    public ResultVO clickLike(@RequestParam("code")String code,@RequestParam("productId")Integer productId) throws IOException {

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        //status为-1取消赞，status为1加一个赞
        if(appreciateService.getByUserIdAndProdId(user.getUserId(),productId)==null){
            Appreciate appreciate = new Appreciate();
            appreciate.setUserId(user.getUserId());
            appreciate.setProdId(productId);
            appreciateService.createAppreciate(appreciate);
        }else{
            appreciateService.deleteByUserIdAndProdId(user.getUserId(),productId);
        }

        return new ResultVO(0,"success");
    }

    /**
     * 3.获取用户全部收藏
     */
    @PostMapping("/getCollects")
    public ResultVO getAllCollects(@RequestParam("code")String code,@RequestParam("pageNum")Integer pageNum,
                                   @RequestParam("pageSize")Integer pageSize) throws IOException {
        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User buyUser = userService.getByUserOpenId(openId);


        Page<Collect> collectPage = collectService.getAllByUserId(buyUser.getUserId(),pageNum,pageSize);
        List<Collect> collectList = collectPage.getContent();

        List<CollectVO> collectVOList = new ArrayList<>();

        for(Collect collect:collectList){

            //先查询商品信息
            Products product = productsService.getOneById(collect.getProdId());

            CollectVO collectVO = new CollectVO();

            //若不存在插入特别信息
            if(product==null){
                collectVO.setProdId(collect.getProdId());
                collectVO.setProdName("该商品已失效");
                collectVO.setSubUser("该商品已失效");
                collectVO.setProdImage("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3766571209,1356236534&fm=26&gp=0.jpg");
                collectVOList.add(collectVO);
                continue;
            }

            User subUser = userService.getByUserId(product.getSubUser());

            collectVO.setProdId(product.getProdId());
            collectVO.setProdName(product.getProdName());
            collectVO.setSubAvatar(subUser.getAvatarUrl());
            collectVO.setSubUser(subUser.getNickname());
            collectVO.setProdImage(product.getProdImg1());
            collectVO.setPrePrice(product.getPrePrice());
            collectVO.setNowPrice(product.getNowPrice());

            collectVOList.add(collectVO);
        }

        CollectDataVO collectDataVO = new CollectDataVO();
        collectDataVO.setCollectVOList(collectVOList);
        collectDataVO.setTotal((int) collectPage.getTotalElements());

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(collectDataVO);

        return resultVO;
    }

}
