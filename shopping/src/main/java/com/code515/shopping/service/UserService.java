package com.code515.shopping.service;

import com.code515.shopping.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    /**
     * 1.根据用户open_id查询
     * @param openId
     * @return
     */
    public User getByUserOpenId(String openId);

    public User getByUserId(Integer id);

    /**
     * 2.添加一个用户
     * @param user
     * @return
     */
    public User createUser(User user);

    /**
     * 3.修改用户信息
     * @param user
     * @return
     */
    public User updateUser(User user);

    /**
     * 4.删除一个用户
     * @param openId
     */
    public void deleteByOpenId(String openId);

    /**
     * 5.获取所有用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<User> getAllUser(Integer pageNum, Integer pageSize);

}
