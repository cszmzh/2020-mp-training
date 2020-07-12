package com.code515.shopping.VO.ProductVO;

import lombok.Data;

@Data
public class ProductDataVO<T> {

    private Integer total;
    private  T productList;

}
