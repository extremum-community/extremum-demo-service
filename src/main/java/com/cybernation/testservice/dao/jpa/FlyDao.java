package com.cybernation.testservice.dao.jpa;

import com.cybernation.testservice.models.jpa.basic.Fly;
import io.extremum.jpa.dao.PostgresCommonDao;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
public interface FlyDao extends PostgresCommonDao<Fly> {
    List<Fly> findBySwarmId(UUID swarmId);

    long countBySwarmId(UUID swarmId);
}
