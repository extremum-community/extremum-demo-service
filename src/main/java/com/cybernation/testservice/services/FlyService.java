package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Fly;
import com.extremum.services.PostgresBasicService;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
public interface FlyService extends PostgresBasicService<Fly> {
    List<Fly> findBySwarmId(UUID swarmId);
}
