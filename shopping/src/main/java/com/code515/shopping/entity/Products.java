package com.code515.shopping.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/*类目表*/
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Products {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer prodId;
  private String prodName;
  private BigDecimal nowPrice;
  private BigDecimal prePrice;
  private Integer cateId;
  private Integer subUser;
  private Integer buyUser;
  private Integer prodStatus;
  private String prodDescription;
  private String subContact;
  private String buyContact;
  private String buyRealname;
  private String buyAddress;
  private String prodImg1;
  private String prodImg2;
  private String prodImg3;
  private String prodImg4;
  private Date buyTime;
  private Date updateTime;
  private Date createTime;
}
