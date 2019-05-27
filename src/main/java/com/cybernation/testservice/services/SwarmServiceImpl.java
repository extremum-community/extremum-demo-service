package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Swarm;
import com.cybernation.testservice.repositories.SwarmDao;
import com.extremum.common.service.impl.PostgresBasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class SwarmServiceImpl extends PostgresBasicServiceImpl<Swarm> implements SwarmService {
    public SwarmServiceImpl(SwarmDao dao) {
        super(dao);
    }
}
