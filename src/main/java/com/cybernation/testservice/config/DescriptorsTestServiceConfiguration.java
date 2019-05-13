package com.cybernation.testservice.config;

import com.extremum.common.repository.BaseMongoRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author rpuch
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = BaseMongoRepository.class,
        basePackages = "com.cybernation.testservice.repositories")
public class DescriptorsTestServiceConfiguration {
}
