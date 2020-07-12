package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.User;
import com.code515.shopping.repository.UserRepository;
import com.code515.shopping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getByUserOpenId(String openId) {
        return repository.findByOpenId(openId);
    }

    @Override
    public User getByUserId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        repository.save(user);
        log.info("【创建用户成功】用户openId: {}",user.getOpenId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userTemp = repository.findByOpenId(user.getOpenId());
        if(userTemp==null){
            log.error("【修改用户信息失败】找不到用户openId：{}",user.getOpenId());
        }
        userTemp.setNickname(user.getNickname());
        userTemp.setAvatarUrl(user.getAvatarUrl());
        userTemp.setGender(user.getGender());
        userTemp.setCountry(user.getCountry());
        userTemp.setProvince(user.getProvince());
        userTemp.setCity(user.getCity());
        repository.save(userTemp);
        log.info("【修改用户信息成功】用户openId：{}",userTemp.getOpenId());
        return userTemp;
    }

    @Override
    public void deleteByOpenId(String openId) {
        repository.deleteByOpenId(openId);
        log.info("【删除用户成功】用户openId: {}",openId);
    }

    @Override
    public Page<User> getAllUser(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<User> page = repository.findAll(pageable);
        return page;
    }
}
