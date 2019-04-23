package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.DemoMongoModel;
import com.extremum.common.dao.MorphiaMongoCommonDao;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Repository;

@Repository
public class DemoMongoModelDao extends MorphiaMongoCommonDao<DemoMongoModel> {
    public DemoMongoModelDao(Datastore datastore) {
        super(datastore);
    }
}
