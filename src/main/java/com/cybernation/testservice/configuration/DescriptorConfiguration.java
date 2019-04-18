package com.cybernation.testservice.configuration;

import com.cybernation.testservice.models.TestMongoModel;
import com.extremum.common.dao.MongoCommonDao;
import org.mongodb.morphia.Datastore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DescriptorConfiguration {
    @Bean
    public MongoCommonDao<TestMongoModel> mongoModelDao(Datastore descriptorsStore) {
        return new MongoCommonDao<TestMongoModel>(descriptorsStore) {
        };
    }
}
