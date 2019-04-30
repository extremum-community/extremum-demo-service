package com.cybernation.testservice.services;

import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.repositories.DemoMongoModelDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemoMongoModelServiceImpl extends MongoCommonServiceImpl<DemoMongoModel> implements DemoMongoService {
    public DemoMongoModelServiceImpl(DemoMongoModelDao dao) {
        super(dao);
    }
}
