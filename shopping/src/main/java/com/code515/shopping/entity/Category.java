package com.code515.shopping.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*类目表*/
@Entity
@Data
@DynamicUpdate
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String mininame;
  private String description;
  private String image;
  private Date updateTime;
  private Date createTime;
}
