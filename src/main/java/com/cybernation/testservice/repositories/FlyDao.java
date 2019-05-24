package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Fly;
import com.extremum.common.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface FlyDao extends SpringDataJpaCommonDao<Fly> {
}
