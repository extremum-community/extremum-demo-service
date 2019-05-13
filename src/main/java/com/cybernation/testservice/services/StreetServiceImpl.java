package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Street;
import com.cybernation.testservice.repositories.StreetDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
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
