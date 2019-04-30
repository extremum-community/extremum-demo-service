package com.cybernation.testservice.services;

import com.cybernation.testservice.models.House;
import com.cybernation.testservice.repositories.HouseDao;
import com.extremum.common.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class HouseServiceImpl extends MongoCommonServiceImpl<House> implements HouseService {
    public HouseServiceImpl(HouseDao dao) {
        super(dao);
    }
}
