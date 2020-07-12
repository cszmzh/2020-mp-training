package com.code515.shopping.controller;

import com.code515.shopping.VO.ProductVO.ProductCardVO;
import com.code515.shopping.VO.ProductVO.ProductDataVO;
import com.code515.shopping.VO.ProductVO.ProductDetailVO;
import com.code515.shopping.VO.ProductVO.ProductVO;
import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.Category;
import com.code515.shopping.entity.Products;
import com.code515.shopping.entity.Record;
import com.code515.shopping.entity.User;
import com.code515.shopping.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private WxService wxService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private AppreciateService appreciateService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 1.获取所有商品信息
     */
    @GetMapping("/getAll")
    public ResultVO getAll(@RequestParam("prodStatus")Integer prodStatus,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        List<Products> productsList = productsService.getAllByProdStatus(prodStatus,pageNum,pageSize).getContent();
        ResultVO resultVO = new ResultVO(0,"success");

        List<ProductVO> productVOList = new ArrayList<>();
        for(Products product : productsList){
            ProductVO productVO = new ProductVO();
            productVO.setProdId(product.getProdId());
            productVO.setProdName(product.getProdName());
            productVO.setNowPrice(product.getNowPrice());
            productVO.setPrePrice(product.getPrePrice());
            productVO.setCateId(product.getCateId());
            productVO.setProdLike(appreciateService.countTotalByProdId(product.getProdId()));
            productVO.setSubAvatar(userService.getByUserId(product.getSubUser()).getAvatarUrl());
            productVO.setProdImg1(product.getProdImg1());
            productVO.setUpdateTime(product.getUpdateTime());
            productVO.setCreateTime(product.getCreateTime());

            productVOList.add(productVO);
        }

        resultVO.setData(productVOList);
        return resultVO;
    }

    /**
     * 2.根据code和status查询所有发布商品，1为正在发布，2为其他状态
     */
    @PostMapping("/getSaleList")
    public ResultVO getSaleList(@RequestParam("code")String code,@RequestParam("status")Integer status,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize) throws IOException {
        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Page<Products> productsPage;
        if(status==1){
            productsPage = productsService.getAllBySubIdAndProdStatus(user.getUserId(),1,pageNum,pageSize);
        }else if(status==2){
            productsPage = productsService.getAllBySubIdAndProdStatusNotIn(user.getUserId(),1,pageNum,pageSize);
        }else {
            return new ResultVO(1,"status错误");
        }

        List<Products> productsList = productsPage.getContent();
        List<ProductCardVO> productCardVOList = new ArrayList<>();

        for(Products product : productsList){
            ProductCardVO productCardVO = new ProductCardVO();
            productCardVO.setProdId(product.getProdId());
            productCardVO.setProdName(product.getProdName());
            productCardVO.setPrePrice(product.getPrePrice());
            productCardVO.setNowPrice(product.getNowPrice());
            productCardVO.setProdImage(product.getProdImg1());
            productCardVO.setStatus(product.getProdStatus());
            productCardVO.setTotalLike(appreciateService.countTotalByProdId(product.getProdId()).toString());
            productCardVO.setTotalCollect(collectService.countTotalByProdId(product.getProdId()).toString());

            productCardVOList.add(productCardVO);
        }

        ProductDataVO productDataVO = new ProductDataVO();
        productDataVO.setTotal((int) productsPage.getTotalElements());
        productDataVO.setProductList(productCardVOList);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(productDataVO);

        return resultVO;
    }

    /**
     * 3.查询用户购买商品
     */
    @PostMapping("getBuyList")
    public ResultVO getBuyList(@RequestParam("code") String code,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize) throws IOException {
        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Page<Products> productsPage = productsService.getAllByBuyId(user.getUserId(),pageNum,pageSize);
        List<Products> productsList = productsPage.getContent();

        List<ProductCardVO> productCardVOList = new ArrayList<>();

        for(Products product:productsList){
            ProductCardVO productCardVO = new ProductCardVO();
            productCardVO.setProdId(product.getProdId());
            productCardVO.setProdName(product.getProdName());
            productCardVO.setPrePrice(product.getPrePrice());
            productCardVO.setNowPrice(product.getNowPrice());
            productCardVO.setStatus(product.getProdStatus());
            productCardVO.setProdImage(product.getProdImg1());
            productCardVO.setTotalLike(appreciateService.countTotalByProdId(product.getProdId()).toString());
            productCardVO.setTotalCollect(collectService.countTotalByProdId(product.getProdId()).toString());

            productCardVOList.add(productCardVO);
        }

        ProductDataVO productDataVO = new ProductDataVO();
        productDataVO.setTotal((int) productsPage.getTotalElements());

        productDataVO.setProductList(productCardVOList);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(productDataVO);

        return resultVO;
    }

    /**
     * 4.根据id删除商品
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResultVO deleteById(@RequestParam("prodId")Integer id){
        productsService.deleteById(id);
        return new ResultVO(0,"success");
    }

    /**
     * 5.发布商品
     * @return
     */
    @PostMapping("/create")
    public ResultVO create(@RequestParam("code")String code, @RequestParam("prodName")String prodName, @RequestParam("nowPrice")BigDecimal nowPrice,
                           BigDecimal prePrice, @RequestParam("categoryName")String categoryName,@RequestParam("categoryMininame")String categoryMininame,
                           @RequestParam("prodDescription")String prodDescription, @RequestParam("subContact")String subContact,
                           @RequestParam("prodImg1") String prodImg1,String prodImg2,String prodImg3,String prodImg4) throws IOException {

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);
        Category category = categoryService.getByNameAndMininame(categoryName,categoryMininame);

        Products product = new Products();

        product.setProdName(prodName);
        product.setNowPrice(nowPrice);
        if(prePrice!=null){
            product.setPrePrice(prePrice);
        }
        product.setCateId(category.getId());
        product.setSubUser(user.getUserId());
        product.setProdDescription(prodDescription);
        product.setSubContact(subContact);
        product.setProdImg1(prodImg1);
        if(!prodImg2.equals("null")){
            product.setProdImg2(prodImg2);
        }
        if(!prodImg3.equals("null")){
            product.setProdImg3(prodImg3);
        }
        if(!prodImg4.equals("null")){
            product.setProdImg4(prodImg4);
        }

        productsService.createProduct(product);

        /*时间可能不正确，以后再调*/
        Record recordSale = new Record();
        recordSale.setProdId(product.getProdId());
        recordSale.setUserId(user.getUserId());
        recordSale.setDescription("您发布了商品~");

        recordService.createOne(recordSale);

        return new ResultVO(0, "success");
    }

    /**
     * 6.更新商品
     */
    @PostMapping("/update")
    public ResultVO update(@RequestParam("prodId")Integer prodId, @RequestParam("prodName")String prodName, @RequestParam("nowPrice")BigDecimal nowPrice,
                           BigDecimal prePrice, @RequestParam("categoryName")String categoryName,@RequestParam("categoryMininame")String categoryMininame, @RequestParam("prodDescription")String prodDescription,
                           @RequestParam("subContact")String subContact, String prodImg1,String prodImg2,String prodImg3,String prodImg4){

        Products product = productsService.getOneById(prodId);
        Category category = categoryService.getByNameAndMininame(categoryName,categoryMininame);

        product.setProdName(prodName);
        product.setNowPrice(nowPrice);
        if(prePrice!=null){
            product.setPrePrice(prePrice);
        }
        product.setSubContact(subContact);
        System.out.println(categoryName+categoryMininame);
        product.setCateId(category.getId());
        product.setProdDescription(prodDescription);
//        products.setSubContact(subContact);
//        products.setProdImg1(prodImg1);
//        products.setProdImg2(prodImg2);
//        products.setProdImg3(prodImg3);
//        products.setProdImg4(prodImg4);

        productsService.updateProduct(product);

        return new ResultVO(0, "success");
    }

    /**
     * 7.随机获取已发布商品
     */
    @GetMapping("/recommend")
    public ResultVO getRandomly(@RequestParam("num")Integer num){

        List<Products> productsList = productsService.getProductsRandomly(num);
        ResultVO resultVO = new ResultVO(0,"success");

        List<ProductVO> productVOList = new ArrayList<>();
        for(Products product : productsList){
            ProductVO productVO = new ProductVO();
            productVO.setProdId(product.getProdId());
            productVO.setProdName(product.getProdName());
            productVO.setNowPrice(product.getNowPrice());
            productVO.setPrePrice(product.getPrePrice());
            productVO.setCateId(product.getCateId());
            productVO.setProdLike(appreciateService.countTotalByProdId(product.getProdId()));
            productVO.setSubAvatar(userService.getByUserId(product.getSubUser()).getAvatarUrl());
            productVO.setProdImg1(product.getProdImg1());
            productVO.setUpdateTime(product.getUpdateTime());
            productVO.setCreateTime(product.getCreateTime());

            productVOList.add(productVO);
        }

        resultVO.setData(productVOList);
        return resultVO;

    }

    /**
     * 8.根据商品ID获取详细信息
     */
    @GetMapping("/getDetailById")
    public ResultVO getDetailById(@RequestParam("id")Integer id, @RequestParam("code")String code) throws IOException {

        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        Products product = productsService.getOneById(id);

        ResultVO resultVO = new ResultVO(0,"success");

        ProductDetailVO productDetailVO = new ProductDetailVO();

        productDetailVO.setProdId(product.getProdId());
        productDetailVO.setProdName(product.getProdName());
        productDetailVO.setNowPrice(product.getNowPrice());
        productDetailVO.setPrePrice(product.getPrePrice());
        productDetailVO.setCateId(product.getCateId());
        productDetailVO.setSubUser(userService.getByUserId(product.getSubUser()).getNickname());
        if(product.getBuyUser()!=null){
            productDetailVO.setBuyUser(userService.getByUserId(product.getBuyUser()).getNickname());
        }
        productDetailVO.setProdStatus(product.getProdStatus());
        productDetailVO.setProdDescription(product.getProdDescription());
        productDetailVO.setProdLike(appreciateService.countTotalByProdId(product.getProdId()));
        productDetailVO.setProdCollect(0);  //等待完成
        productDetailVO.setSubContact(product.getSubContact());
        productDetailVO.setBuyContact(product.getBuyContact());
        productDetailVO.setSubAvatar(userService.getByUserId(product.getSubUser()).getAvatarUrl());
        if(product.getBuyUser()!=null){
            productDetailVO.setBuyAvatar(userService.getByUserId(product.getBuyUser()).getAvatarUrl());
        }
        productDetailVO.setBuyAddress(product.getBuyAddress());
        productDetailVO.setBuyRealname(product.getBuyRealname());
        productDetailVO.setProdImg1(product.getProdImg1());
        productDetailVO.setProdImg2(product.getProdImg2());
        productDetailVO.setProdImg3(product.getProdImg3());
        productDetailVO.setProdImg4(product.getProdImg4());
        productDetailVO.setUpdateTime(product.getUpdateTime());
        productDetailVO.setCreateTime(product.getCreateTime());
        productDetailVO.setBuyTime(product.getBuyTime());

        //设置收藏与点赞
        if(appreciateService.getByUserIdAndProdId(user.getUserId(),product.getProdId())==null){
            productDetailVO.setIsAppreciate(false);
        }else {
            productDetailVO.setIsAppreciate(true);
        }

        if(collectService.getByUserIdAndProdId(user.getUserId(),product.getProdId())==null){
            productDetailVO.setIsCollect(false);
        }else {
            productDetailVO.setIsCollect(true);
        }

        resultVO.setData(productDetailVO);
        return resultVO;
    }

    /**
     * 9.根据关键字查找所有商品
     */
    @GetMapping("/search")
    public ResultVO getAllByProdNameLike(@RequestParam("prodName")String prodName){
        List<Products> productsList = productsService.getProductsByNameLike(prodName);

        List<ProductVO> productVOList = new ArrayList<>();
        for(Products product : productsList){
            ProductVO productVO = new ProductVO();
            productVO.setProdId(product.getProdId());
            productVO.setProdName(product.getProdName());
            productVO.setNowPrice(product.getNowPrice());
            productVO.setPrePrice(product.getPrePrice());
            productVO.setCateId(product.getCateId());
            productVO.setProdLike(appreciateService.countTotalByProdId(product.getProdId()));
            productVO.setSubAvatar(userService.getByUserId(product.getSubUser()).getAvatarUrl());
            productVO.setProdImg1(product.getProdImg1());
            productVO.setUpdateTime(product.getUpdateTime());
            productVO.setCreateTime(product.getCreateTime());

            productVOList.add(productVO);
        }

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(productVOList);
        return resultVO;
    }

    /**
     * 10.根据商品分类查找所有商品（已上架）
     */
    @GetMapping("getAllByCatId")
    public ResultVO getAllByCatId(@RequestParam("catId")Integer catId){

        //根据分类id查询100条
        List<Products> productsList = productsService.getAllByCategoryId(catId,0,100).getContent();

        List<ProductVO> productVOList = new ArrayList<>();
        for(Products product : productsList){
            ProductVO productVO = new ProductVO();
            productVO.setProdId(product.getProdId());
            productVO.setProdName(product.getProdName());
            productVO.setNowPrice(product.getNowPrice());
            productVO.setPrePrice(product.getPrePrice());
            productVO.setCateId(product.getCateId());
            productVO.setProdLike(appreciateService.countTotalByProdId(product.getProdId()));
            productVO.setSubAvatar(userService.getByUserId(product.getSubUser()).getAvatarUrl());
            productVO.setProdImg1(product.getProdImg1());
            productVO.setUpdateTime(product.getUpdateTime());
            productVO.setCreateTime(product.getCreateTime());

            productVOList.add(productVO);
        }

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(productVOList);
        return resultVO;
    }
}
