package com.cybernation.testservice.services.collectionfetcher;

import com.cybernation.testservice.models.Fly;
import com.cybernation.testservice.models.Swarm;
import com.cybernation.testservice.services.FlyService;
import com.extremum.everything.collection.Projection;
import com.extremum.everything.services.CollectionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String getHostFieldName() {
        return Swarm.CUSTOM_FLIES;
    }

    @Override
    public List<Fly> fetchCollection(Swarm swarm, Projection projection) {
        return flyService.findBySwarmId(swarm.getId());
    }
}
