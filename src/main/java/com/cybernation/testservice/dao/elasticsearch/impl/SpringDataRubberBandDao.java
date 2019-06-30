package com.cybernation.testservice.dao.elasticsearch.impl;

import com.cybernation.testservice.dao.elasticsearch.RubberBandDao;
import com.cybernation.testservice.models.elasticsearch.RubberBand;
import com.extremum.elasticsearch.dao.impl.SpringDataElasticsearchCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataRubberBandDao extends RubberBandDao, SpringDataElasticsearchCommonDao<RubberBand> {
}
