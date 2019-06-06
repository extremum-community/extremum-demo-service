package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.jpa.services.PostgresBasicService;

/**
 * @author rpuch
 */
public interface SwarmService extends PostgresBasicService<Swarm> {
    Swarm findAsJpaReference(String id);
}
