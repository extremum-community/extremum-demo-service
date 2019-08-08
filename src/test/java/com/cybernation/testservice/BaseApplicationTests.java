package com.cybernation.testservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.stream.Stream;

public abstract class BaseApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApplicationTests.class);

    private static final GenericContainer redis = new GenericContainer("redis:5.0.4")
            .withExposedPorts(6379);
    private static final GenericContainer mongo = new GenericContainer("mongo:3.4-xenial")
            .withExposedPorts(27017);
    private static final GenericContainer postgres = new GenericContainer("postgres:11.3-alpine")
            .withExposedPorts(5432);

    static {
        Stream.of(redis, mongo, postgres).forEach(GenericContainer::start);
        System.setProperty("redis.uri", "redis://" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort());
        String mongoUri = "mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getFirstMappedPort();
        System.setProperty("mongo.service-db--uri", mongoUri);
        System.setProperty("mongo.descriptors-db-uri", mongoUri);
        String postgresUrl = String.format("jdbc:postgresql://%s:%d/%s",
                postgres.getContainerIpAddress(), postgres.getFirstMappedPort(), "postgres");
        System.setProperty("jpa.uri", postgresUrl);
        LOGGER.info("Postgres DB url is {}", postgresUrl);

        startElasticsearch();
    }

    private static void startElasticsearch() {
        workaroundElasticsearchClientStartupQuirk();

        if ("true".equals(System.getProperty("start.elasticsearch", "true"))) {
            ElasticsearchContainer elasticSearch = new ElasticsearchContainer("elasticsearch:7.1.0");
            elasticSearch.start();

            System.setProperty("elasticsearch.hosts[0].host", elasticSearch.getContainerIpAddress());
            System.setProperty("elasticsearch.hosts[0].port", Integer.toString(elasticSearch.getFirstMappedPort()));
            System.setProperty("elasticsearch.hosts[0].protocol", "http");

            LOGGER.info("Elasticsearch host:port are {}:{}",
                    elasticSearch.getContainerIpAddress(), elasticSearch.getFirstMappedPort());
        } else {
            System.setProperty("elasticsearch.hosts[0].host", "localhost");
            System.setProperty("elasticsearch.hosts[0].port", "9200");
            System.setProperty("elasticsearch.hosts[0].protocol", "http");
        }
    }

    private static void workaroundElasticsearchClientStartupQuirk() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
