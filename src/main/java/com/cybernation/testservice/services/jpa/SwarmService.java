package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.models.jpa.basic.Swarm;
import io.extremum.jpa.service.PostgresBasicService;

/**
 * @author rpuch
 */
public interface SwarmService extends PostgresBasicService<Swarm> {
    Swarm findAsJpaReference(String id);
}
