package com.cybernation.testservice.services.mongo;

import com.cybernation.testservice.models.mongo.Street;
import com.cybernation.testservice.dao.mongo.StreetDao;
import io.extremum.mongo.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class StreetServiceImpl extends MongoCommonServiceImpl<Street> implements StreetService {
    public StreetServiceImpl(StreetDao dao) {
        super(dao);
    }
}
