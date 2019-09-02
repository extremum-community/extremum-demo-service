package com.cybernation.testservice.services.auth;

import com.cybernation.testservice.models.auth.TopSecret;
import io.extremum.mongo.dao.MongoCommonDao;
import io.extremum.mongo.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class TopSecretServiceImpl extends MongoCommonServiceImpl<TopSecret> implements TopSecretService {
    public TopSecretServiceImpl(MongoCommonDao<TopSecret> dao) {
        super(dao);
    }
}
