package com.cybernation.testservice.services.auth;

import com.cybernation.testservice.models.auth.TopSecret;
import com.extremum.common.dao.MongoCommonDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
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
