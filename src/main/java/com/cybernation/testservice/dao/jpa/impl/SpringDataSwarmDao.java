package com.cybernation.testservice.dao.jpa.impl;

import com.cybernation.testservice.dao.jpa.SwarmDao;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import io.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
public interface SpringDataSwarmDao extends SwarmDao, SpringDataJpaCommonDao<Swarm> {
}
