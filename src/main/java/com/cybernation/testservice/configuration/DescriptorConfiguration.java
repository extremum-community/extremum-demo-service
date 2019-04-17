package com.cybernation.testservice.configuration;

import com.cybernation.testservice.models.TestMongoModel;
import com.extremum.common.dao.MongoCommonDao;
import com.extremum.common.descriptor.dao.DescriptorDao;
import com.extremum.common.descriptor.dao.impl.BaseDescriptorDaoImpl;
import com.extremum.common.descriptor.serde.mongo.DescriptorConverter;
import com.extremum.common.descriptor.serde.mongo.DescriptorCreator;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.io.IOException;
import java.io.InputStream;


@Configuration
@EnableConfigurationProperties({RedisProperties.class, MongoProperties.class, DescriptorsProperties.class})
public class DescriptorConfiguration {
    private final RedisProperties redisProps;
    private final MongoProperties mongoProps;
    private final DescriptorsProperties descriptorsProperties;

    public DescriptorConfiguration(RedisProperties redisProps, MongoProperties mongoProps, DescriptorsProperties descriptorsProperties) {
        this.redisProps = redisProps;
        this.mongoProps = mongoProps;
        this.descriptorsProperties = descriptorsProperties;
    }

    @Bean
    public RedissonClient redissonClient() {
        InputStream redisStream = DescriptorConfiguration.class.getClassLoader().getResourceAsStream("redis.json");
        Config config;
        try {
            config = Config.fromJSON(redisStream);
        } catch (IOException e) {
            config = new Config();
        }
        config.useSingleServer().setAddress(redisProps.getUri());
        return Redisson.create(config);
    }

    @Bean
    public Datastore descriptorsStore() {
        Morphia morphia = new Morphia();
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setObjectFactory(new DescriptorCreator());
        morphia.getMapper().getConverters().addConverter(DescriptorConverter.class);
        MongoClientURI databaseUri = new MongoClientURI(mongoProps.getUri());
        MongoClient mongoClient = new MongoClient(databaseUri);
        Datastore datastore = morphia.createDatastore(mongoClient, mongoProps.getDbName());

        datastore.ensureIndexes();

        return datastore;
    }

    @Bean
    public DescriptorDao descriptorDao(RedissonClient redissonClient, Datastore descriptorsStore) {
        return new BaseDescriptorDaoImpl(redissonClient, descriptorsStore, descriptorsProperties.getDescriptorsMapName(),
                descriptorsProperties.getInternalIdsMapName(), redisProps.getCacheSize(), redisProps.getIdleTime());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new RedissonConnectionFactory(redissonClient());
    }

    @Bean
    public MongoCommonDao<TestMongoModel> mongoModelDao() {
        return new MongoCommonDao<TestMongoModel>(descriptorsStore()) {
        };
    }
}
