package com.cybernation.testservice.dao.mongo.impl;

import com.cybernation.testservice.dao.mongo.StreetDao;
import com.cybernation.testservice.models.mongo.Street;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataStreetDao extends StreetDao, SpringDataMongoCommonDao<Street> {
}