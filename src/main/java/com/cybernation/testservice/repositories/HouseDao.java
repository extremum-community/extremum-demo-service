package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.House;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface HouseDao extends SpringDataMongoCommonDao<House> {
}
