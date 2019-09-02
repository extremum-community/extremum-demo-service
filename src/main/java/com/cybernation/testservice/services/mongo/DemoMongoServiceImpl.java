package com.cybernation.testservice.services.mongo;

import com.cybernation.testservice.models.mongo.DemoMongoModel;
import com.cybernation.testservice.dao.mongo.DemoMongoModelDao;
import io.extremum.mongo.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemoMongoServiceImpl extends MongoCommonServiceImpl<DemoMongoModel> implements DemoMongoService {
    public DemoMongoServiceImpl(DemoMongoModelDao dao) {
        super(dao);
    }
}
