package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.FlyDao;
import com.cybernation.testservice.models.jpa.basic.Fly;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.jpa.services.impl.PostgresBasicServiceImpl;
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
    public CollectionFragment<Fly> findBySwarmId(UUID swarmId) {
        List<Fly> flies = flyDao.findBySwarmId(swarmId);
        long total = flyDao.countBySwarmId(swarmId);
        return CollectionFragment.forFragment(flies, total);
    }
}
