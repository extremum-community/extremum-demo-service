package com.cybernation.testservice.services.mongo;

import com.cybernation.testservice.models.mongo.House;
import com.cybernation.testservice.dao.mongo.HouseDao;
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
