package com.cybernation.testservice.dao.mongo;

import com.cybernation.testservice.models.mongo.DemoMongoModel;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoMongoModelDao extends SpringDataMongoCommonDao<DemoMongoModel> {
}
