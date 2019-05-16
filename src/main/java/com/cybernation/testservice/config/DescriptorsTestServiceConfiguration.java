package com.cybernation.testservice.config;

import com.extremum.common.repository.jpa.BaseJpaRepository;
import com.extremum.common.repository.mongo.BaseMongoRepository;
import com.extremum.common.repository.mongo.SoftDeleteMongoRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author rpuch
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = BaseMongoRepository.class,
        repositoryFactoryBeanClass = SoftDeleteMongoRepositoryFactoryBean.class,
        basePackages = "com.cybernation.testservice.repositories")
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepository.class,
        basePackages = "com.cybernation.testservice.repositories")
public class DescriptorsTestServiceConfiguration {
}
