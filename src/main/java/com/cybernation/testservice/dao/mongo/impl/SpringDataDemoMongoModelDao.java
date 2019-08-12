package com.cybernation.testservice.dao.mongo.impl;

import com.cybernation.testservice.dao.mongo.DemoMongoModelDao;
import com.cybernation.testservice.models.mongo.DemoMongoModel;
import io.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

@Repository
interface SpringDataDemoMongoModelDao extends DemoMongoModelDao, SpringDataMongoCommonDao<DemoMongoModel> {
}
