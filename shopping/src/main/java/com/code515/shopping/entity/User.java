package com.code515.shopping.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*用户表*/
@Entity
@Data
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String openId;
    private String nickname;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private Date updateTime;
    private Date createTime;
}

