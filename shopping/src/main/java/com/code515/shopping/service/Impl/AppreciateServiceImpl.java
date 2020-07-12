package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.Appreciate;
import com.code515.shopping.repository.AppreciateRepository;
import com.code515.shopping.service.AppreciateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppreciateServiceImpl implements AppreciateService {

    @Autowired
    private AppreciateRepository repository;

    @Override
    public Appreciate getByUserIdAndProdId(Integer userId, Integer prodId) {
        return repository.findByUserIdAndProdId(userId, prodId);
    }

    @Override
    public List<Appreciate> getAllByUserId(Integer userId) {
        return repository.findAllByUserIdOrderByCreateTimeDesc(userId);
    }

    @Override
    public void deleteByUserIdAndProdId(Integer userId, Integer prodId) {
        repository.deleteByUserIdAndProdId(userId, prodId);
    }

    @Override
    public Appreciate createAppreciate(Appreciate appreciate) {
        repository.save(appreciate);
        return appreciate;
    }

    @Override
    public Integer countTotalByProdId(Integer prodId) {
        return repository.countAllByProdId(prodId);
    }
}
