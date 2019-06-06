package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.SwarmDao;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.jpa.services.impl.PostgresBasicServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author rpuch
 */
@Service
public class SwarmServiceImpl extends PostgresBasicServiceImpl<Swarm> implements SwarmService {
    private final JpaRepository<Swarm, UUID> repositoryToTestGetOne;

    public SwarmServiceImpl(SwarmDao dao, JpaRepository<Swarm, UUID> repositoryToTestGetOne) {
        super(dao);
        this.repositoryToTestGetOne = repositoryToTestGetOne;
    }

    @Override
    public Swarm findAsJpaReference(String id) {
        return repositoryToTestGetOne.getOne(UUID.fromString(id));
    }
}
