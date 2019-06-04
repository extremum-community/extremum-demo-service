package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.SwarmDao;
import com.cybernation.testservice.dao.jpa.impl.SpringDataSwarmDao;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.jpa.services.impl.PostgresBasicServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author rpuch
 */
@Service
public class SwarmServiceImpl extends PostgresBasicServiceImpl<Swarm> implements SwarmService {
    private final SpringDataSwarmDao repository;

    public SwarmServiceImpl(SwarmDao dao, SpringDataSwarmDao repository) {
        super(dao);
        this.repository = repository;
    }

    @Override
    public Swarm findAsJpaReference(String id) {
        return repository.getOne(UUID.fromString(id));
    }
}
