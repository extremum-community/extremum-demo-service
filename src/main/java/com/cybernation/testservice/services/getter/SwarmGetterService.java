package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.cybernation.testservice.services.jpa.SwarmService;
import io.extremum.everything.services.GetterService;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class SwarmGetterService implements GetterService<Swarm> {
    private final SwarmService swarmService;

    public SwarmGetterService(SwarmService swarmService) {
        this.swarmService = swarmService;
    }

    @Override
    public Swarm get(String id) {
        return swarmService.findAsJpaReference(id);
    }

    @Override
    public String getSupportedModel() {
        return Swarm.MODEL_NAME;
    }
}
