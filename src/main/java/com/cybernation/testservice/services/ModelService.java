package com.cybernation.testservice.services;

import com.cybernation.testservice.models.TestMongoModel;
import com.extremum.common.dao.MongoCommonDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ModelService extends MongoCommonServiceImpl<TestMongoModel> {
    public ModelService(MongoCommonDao<TestMongoModel> dao) {
        super(dao);
    }
}
