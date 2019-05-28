package com.cybernation.testservice.dao.mongo;

import com.cybernation.testservice.models.mongo.House;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface HouseDao extends SpringDataMongoCommonDao<House> {
}
