package com.code515.shopping.VO.ProductVO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDetailVO {
    private Integer prodId;
    private String prodName;
    private BigDecimal nowPrice;
    private BigDecimal prePrice;
    private Integer cateId;
    private String subUser;
    private String buyUser;
    private Integer prodStatus;
    private String prodDescription;
    private Integer prodLike;
    private Integer prodCollect;
    private String subContact;
    private String buyContact;
    private String subAvatar;
    private String buyAvatar;
    private String buyRealname;
    private String buyAddress;
    private String prodImg1;
    private String prodImg2;
    private String prodImg3;
    private String prodImg4;
    private Date buyTime;
    private Date updateTime;
    private Date createTime;
    private Boolean isCollect;
    private Boolean isAppreciate;
}
