package com.cybernation.testservice.dao.jpa;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
@Repository
public interface FlyDao extends SpringDataJpaCommonDao<Fly> {
    List<Fly> findBySwarmId(UUID swarmId);

    long countBySwarmId(UUID swarmId);
}
