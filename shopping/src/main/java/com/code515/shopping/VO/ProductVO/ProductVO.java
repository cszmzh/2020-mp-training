package com.code515.shopping.VO.ProductVO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductVO {
    private Integer prodId;
    private String prodName;
    private BigDecimal nowPrice;
    private BigDecimal prePrice;
    private Integer cateId;
    private Integer prodLike;
    private String subAvatar;
    private String prodImg1;
    private Date updateTime;
    private Date createTime;
}
