package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Swarm;
import com.extremum.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface SwarmDao extends SpringDataJpaCommonDao<Swarm> {
}
