package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Fly;
import com.cybernation.testservice.repositories.FlyDao;
import com.extremum.common.service.impl.PostgresBasicServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
@Service
public class FlyServiceImpl extends PostgresBasicServiceImpl<Fly> implements FlyService {
    private final FlyDao flyDao;

    public FlyServiceImpl(FlyDao dao) {
        super(dao);
        this.flyDao = dao;
    }

    @Override
    public List<Fly> findBySwarmId(UUID swarmId) {
        return flyDao.findBySwarmId(swarmId);
    }
}
