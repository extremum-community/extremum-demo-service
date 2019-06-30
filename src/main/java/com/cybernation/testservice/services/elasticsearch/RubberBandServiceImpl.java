package com.cybernation.testservice.services.elasticsearch;

import com.cybernation.testservice.dao.elasticsearch.RubberBandDao;
import com.cybernation.testservice.models.elasticsearch.RubberBand;
import com.extremum.elasticsearch.service.impl.ElasticsearchCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class RubberBandServiceImpl extends ElasticsearchCommonServiceImpl<RubberBand> implements RubberBandService {
    public RubberBandServiceImpl(RubberBandDao dao) {
        super(dao);
    }
}
