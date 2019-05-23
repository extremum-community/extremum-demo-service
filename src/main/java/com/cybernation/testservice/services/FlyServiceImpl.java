package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Fly;
import com.cybernation.testservice.repositories.FlyDao;
import com.extremum.common.service.impl.PostgresBasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class FlyServiceImpl extends PostgresBasicServiceImpl<Fly> implements FlyService {
    public FlyServiceImpl(FlyDao dao) {
        super(dao);
    }
}
