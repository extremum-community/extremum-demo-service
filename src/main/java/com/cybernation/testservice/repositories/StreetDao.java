package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Street;
import com.extremum.common.dao.MorphiaMongoCommonDao;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public class StreetDao extends MorphiaMongoCommonDao<Street> {
    public StreetDao(Datastore datastore) {
        super(datastore);
    }
}
