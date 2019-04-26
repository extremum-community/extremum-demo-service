package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.House;
import com.extremum.common.dao.MorphiaMongoCommonDao;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public class HouseDao extends MorphiaMongoCommonDao<House> {
    public HouseDao(Datastore datastore) {
        super(datastore);
    }
}
