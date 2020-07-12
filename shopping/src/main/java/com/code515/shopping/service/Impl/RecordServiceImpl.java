package com.code515.shopping.service.Impl;

import com.code515.shopping.entity.Record;
import com.code515.shopping.repository.RecordRepository;
import com.code515.shopping.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository repository;

    @Override
    public List<Record> getAllByUserIdAndProductId(Integer userId, Integer productId) {
        return repository.findAllByUserIdAndProdIdOrderByCreateTimeDesc(userId, productId);
    }

    @Override
    public Record createOne(Record record) {

        System.out.println(record.getDescription());

        repository.save(record);

        return record;
    }
}
