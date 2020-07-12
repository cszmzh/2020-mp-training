package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.Category;
import com.code515.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 1.通过id获取分类信息
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id")Integer id){
        Category category = categoryService.getByCategoryId(id);
        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(category);
        return resultVO;
    }

    @GetMapping("/getAll")
    public ResultVO getAll(){
        List<Category> categoryList = categoryService.getAll();
        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(categoryList);
        return resultVO;
    }

    @GetMapping("/getByNameAndMiniName")
    public ResultVO getByNameAndMiniName(@RequestParam("name")String name,@RequestParam("mininame")String mininame){
        Category category = categoryService.getByNameAndMininame(name,mininame);
        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(category);
        return resultVO;
    }

}
