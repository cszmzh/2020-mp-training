package com.code515.shopping.repository;

import com.code515.shopping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByOpenId(String openId);

    Page<User> findAll(Pageable pageable);

    @Transactional
    void deleteByOpenId(String openId);

}
