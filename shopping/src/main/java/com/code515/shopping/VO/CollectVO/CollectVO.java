package com.code515.shopping.VO.CollectVO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CollectVO {

    private Integer prodId;
    private String prodName;
    private String subAvatar;
    private String subUser;
    private String prodImage;
    private BigDecimal prePrice;
    private BigDecimal nowPrice;
}
