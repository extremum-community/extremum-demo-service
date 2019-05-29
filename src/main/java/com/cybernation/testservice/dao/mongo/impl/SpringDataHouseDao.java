package com.cybernation.testservice.dao.mongo.impl;

import com.cybernation.testservice.dao.mongo.HouseDao;
import com.cybernation.testservice.models.mongo.House;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataHouseDao extends HouseDao, SpringDataMongoCommonDao<House> {
}
