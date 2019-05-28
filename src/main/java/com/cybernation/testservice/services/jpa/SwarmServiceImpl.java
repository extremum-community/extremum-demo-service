package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.SwarmDao;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.jpa.services.impl.PostgresBasicServiceImpl;
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
