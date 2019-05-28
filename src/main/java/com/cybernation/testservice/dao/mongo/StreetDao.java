package com.cybernation.testservice.dao.mongo;

import com.cybernation.testservice.models.mongo.Street;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface StreetDao extends SpringDataMongoCommonDao<Street> {
}
