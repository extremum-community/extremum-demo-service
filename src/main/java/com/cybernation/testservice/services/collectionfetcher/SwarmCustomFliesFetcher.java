package com.cybernation.testservice.services.collectionfetcher;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.cybernation.testservice.services.jpa.FlyService;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.everything.collection.Projection;
import io.extremum.everything.services.CollectionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
@RequiredArgsConstructor
public class SwarmCustomFliesFetcher implements CollectionFetcher<Swarm, Fly> {
    private final FlyService flyService;

    @Override
    public String getSupportedModel() {
        return Swarm.MODEL_NAME;
    }

    @Override
    public String getHostAttributeName() {
        return Swarm.CUSTOM_FLIES;
    }

    @Override
    public CollectionFragment<Fly> fetchCollection(Swarm swarm, Projection projection) {
        return flyService.findBySwarmId(swarm.getId());
    }
}
