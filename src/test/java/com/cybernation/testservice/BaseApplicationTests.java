package com.cybernation.testservice;

import org.testcontainers.containers.GenericContainer;

import java.util.stream.Stream;

abstract class BaseApplicationTests {
    private static final GenericContainer redis = new GenericContainer("redis:5.0.4")
            .withExposedPorts(6379);
    private static final GenericContainer mongo = new GenericContainer("mongo:3.4-xenial")
            .withExposedPorts(27017);
    private static final GenericContainer postgres = new GenericContainer("postgres:11.3-alpine")
            .withExposedPorts(5432);

    static {
        Stream.of(redis, mongo, postgres).forEach(GenericContainer::start);
        System.setProperty("redis.uri", "redis://" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort());
        System.setProperty("mongo.uri", "mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getFirstMappedPort());
        String postgresUrl = String.format("jdbc:postgresql://%s:%d/%s",
                postgres.getContainerIpAddress(), postgres.getFirstMappedPort(), "postgres");
        System.setProperty("jpa.uri", postgresUrl);
    }
}
