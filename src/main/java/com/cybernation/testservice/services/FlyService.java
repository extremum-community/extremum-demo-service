package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Fly;
import com.extremum.services.PostgresBasicService;
import com.extremum.everything.collection.CollectionFragment;

import java.util.UUID;

/**
 * @author rpuch
 */
public interface FlyService extends PostgresBasicService<Fly> {
    CollectionFragment<Fly> findBySwarmId(UUID swarmId);
}
