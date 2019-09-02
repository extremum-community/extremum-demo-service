package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.models.jpa.basic.Fly;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.jpa.service.PostgresBasicService;

import java.util.UUID;

/**
 * @author rpuch
 */
public interface FlyService extends PostgresBasicService<Fly> {
    CollectionFragment<Fly> findBySwarmId(UUID swarmId);
}
