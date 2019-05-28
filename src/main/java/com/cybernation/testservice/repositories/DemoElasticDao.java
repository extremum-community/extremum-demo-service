package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.DemoElasticModel;
import com.extremum.common.dao.DefaultElasticCommonDao;
import com.extremum.common.descriptor.factory.impl.ElasticDescriptorFactory;
import com.extremum.starter.properties.ElasticProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DemoElasticDao extends DefaultElasticCommonDao<DemoElasticModel> {
    protected DemoElasticDao(ElasticProperties elasticProperties, ElasticDescriptorFactory descriptorFactory,
                             ObjectMapper mapper) {
        super(elasticProperties, descriptorFactory, mapper, "data", "idx");
    }
}
