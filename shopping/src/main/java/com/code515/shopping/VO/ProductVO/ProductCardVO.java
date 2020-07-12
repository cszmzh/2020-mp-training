package com.code515.shopping.VO.ProductVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**用于个人中心模块，我的发布-我的卖出-我的买到*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCardVO {

    private Integer prodId;
    private String prodName;
    private String prodImage;
    private BigDecimal prePrice;
    private BigDecimal nowPrice;
    private String totalLike;
    private String totalCollect;
    private Integer status;
}
