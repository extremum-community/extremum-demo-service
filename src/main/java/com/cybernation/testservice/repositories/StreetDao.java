package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Street;
import com.extremum.common.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface StreetDao extends SpringDataMongoCommonDao<Street> {
}
