package com.cybernation.testservice.services.watch;

import com.cybernation.testservice.models.watch.Spectacle;
import io.extremum.common.dao.MongoCommonDao;
import io.extremum.common.service.impl.MongoCommonServiceImpl;
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
