package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.Collect;
import com.code515.shopping.repository.CollectRepository;
import com.code515.shopping.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectRepository repository;

    @Override
    public Collect getByUserIdAndProdId(Integer userId, Integer prodId) {
        return repository.findByUserIdAndProdId(userId, prodId);
    }

    @Override
    public Page<Collect> getAllByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return repository.findAllByUserIdOrderByCreateTimeDesc(userId, pageable);
    }

    @Override
    public void deleteByUserIdAndProdId(Integer userId, Integer prodId) {
        repository.deleteByUserIdAndProdId(userId, prodId);
    }

    @Override
    public Collect createCollect(Collect collect) {
        repository.save(collect);
        return collect;
    }

    @Override
    public Integer countTotalByProdId(Integer prodId) {
        return repository.countAllByProdId(prodId);
    }
}
