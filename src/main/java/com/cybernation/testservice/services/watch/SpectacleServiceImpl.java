package com.cybernation.testservice.services.watch;

import com.cybernation.testservice.models.watch.Spectacle;
import com.extremum.common.dao.MongoCommonDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class SpectacleServiceImpl extends MongoCommonServiceImpl<Spectacle> implements SpectacleService {
    public SpectacleServiceImpl(MongoCommonDao<Spectacle> dao) {
        super(dao);
    }
}
