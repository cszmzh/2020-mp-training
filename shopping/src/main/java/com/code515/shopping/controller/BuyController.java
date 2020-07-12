package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.Products;
import com.code515.shopping.entity.Record;
import com.code515.shopping.entity.User;
import com.code515.shopping.service.ProductsService;
import com.code515.shopping.service.RecordService;
import com.code515.shopping.service.UserService;
import com.code515.shopping.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/buy")
public class BuyController {

    @Autowired
    private WxService wxService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ProductsService productsService;

    /**
     * 1.新下单
     */
    @PostMapping("/newOrder")
    public ResultVO newOrder(@RequestParam("code")String code, @RequestParam("productId")Integer productId,
                             @RequestParam("contact")String buyContact,@RequestParam("address")String buyAddress,@RequestParam("buyerName")String buyerName) throws IOException {

        //参数验证
        if(buyContact.equals("") || buyerName.equals("")){
            return new ResultVO(1,"参数错误");
        }

        Products product = productsService.getOneById(productId);

        //检验商品是否购买
        if(product.getProdStatus()!=1){
            return new ResultVO(2,"商品已卖出");
        }

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        product.setBuyContact(buyContact);
        Date date = new Date();
        product.setProdStatus(2);
        product.setBuyUser(user.getUserId());
        product.setBuyAddress(buyAddress);
        product.setBuyRealname(buyerName);
        product.setBuyTime(date);

        productsService.updateProduct(product);

        //保存记录
        Record recordBuy = new Record();
        recordBuy.setProdId(product.getProdId());
        recordBuy.setUserId(user.getUserId());
        recordBuy.setCreateTime(date);
        recordBuy.setDescription("您下了新订单，请联系卖家电话" + product.getSubContact());

        recordService.createOne(recordBuy);

        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(product.getSubUser());
        recordSale.setCreateTime(date);
        recordSale.setDescription("买家 "+user.getNickname()+" 下单了您的商品，请联系买家电话："+ buyContact);

        recordService.createOne(recordSale);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(date);
        return resultVO;
    }

    /**
     * 2.卖家取消订单
     * @return
     */
    @PostMapping("/subCancelOrder")
    public ResultVO subCancelOrder(@RequestParam("code")String code, @RequestParam("productId")Integer productId) throws IOException {

        Date date = new Date();

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Products product = productsService.getOneById(productId);
        product.setProdStatus(5);
        productsService.updateProduct(product);

        //保存记录
        Record recordBuy = new Record();
        recordBuy.setProdId(product.getProdId());
        recordBuy.setUserId(user.getUserId());
        recordBuy.setDescription("卖家 "+ user.getNickname() +" 取消了订单~");
        recordBuy.setCreateTime(date);

        recordService.createOne(recordBuy);

        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(user.getUserId());
        recordSale.setDescription("您取消了订单~");
        recordSale.setCreateTime(date);

        recordService.createOne(recordSale);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(date);
        return resultVO;
    }

    /**
     * 3.买家取消订单
     */
    @PostMapping("/buyCancelOrder")
    public ResultVO buyCancelOrder(@RequestParam("code")String code, @RequestParam("productId")Integer productId) throws IOException {

        Date date = new Date();

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Products product = productsService.getOneById(productId);
        product.setProdStatus(4);
        productsService.updateProduct(product);

        //保存记录
        Record recordBuy = new Record();
        recordBuy.setProdId(product.getProdId());
        recordBuy.setUserId(product.getBuyUser());
        recordBuy.setDescription("您取消了订单~");
        recordBuy.setCreateTime(date);

        recordService.createOne(recordBuy);

        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(user.getUserId());
        recordSale.setDescription("买家 "+user.getNickname()+" 取消了该订单~");
        recordSale.setCreateTime(date);

        recordService.createOne(recordSale);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(date);
        return resultVO;
    }

    /**
     * 4.卖家发货
     */
    @PostMapping("/sendProduct")
    public ResultVO sendProduct(@RequestParam("code")String code, @RequestParam("prodId")Integer productId) throws IOException {

        Date date = new Date();

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Products product = productsService.getOneById(productId);
        product.setProdStatus(3);
        productsService.updateProduct(product);

        //保存记录
        Record recordBuy = new Record();
        recordBuy.setProdId(product.getProdId());
        recordBuy.setUserId(product.getBuyUser());
        recordBuy.setDescription("卖家 "+user.getNickname()+" 发货啦~");
        recordBuy.setCreateTime(date);

        recordService.createOne(recordBuy);

        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(user.getUserId());
        recordSale.setDescription("您发货啦~ 买家昵称是："+userService.getByUserId(product.getBuyUser()).getNickname()+"，请按照上方买家提供的信息发货哦~");
        recordSale.setCreateTime(date);

        recordService.createOne(recordSale);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(date);
        return resultVO;
    }

    /**
     * 5.买家收货，交易成功
     */
    @PostMapping("/finish")
    public ResultVO finish(@RequestParam("code")String code, @RequestParam("prodId")Integer productId) throws IOException {

        Date date = new Date();

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Products product = productsService.getOneById(productId);
        product.setProdStatus(6);
        productsService.updateProduct(product);

        //保存记录
        Record recordBuy = new Record();
        recordBuy.setProdId(product.getProdId());
        recordBuy.setUserId(product.getBuyUser());
        recordBuy.setDescription("您收货了，交易成功！");
        recordBuy.setCreateTime(date);

        recordService.createOne(recordBuy);

        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(product.getSubUser());
        recordSale.setDescription("买家 "+user.getNickname()+" 收货了，交易成功！");
        recordSale.setCreateTime(date);

        recordService.createOne(recordSale);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(date);
        return resultVO;
    }

}
