package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.DemoMongoModel;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoMongoModelDao extends SpringDataMongoCommonDao<DemoMongoModel> {
}
