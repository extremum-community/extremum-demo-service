package com.cybernation.testservice.dao.auth;

import com.cybernation.testservice.models.auth.User;
import io.extremum.mongo.dao.MongoCommonDao;

import java.util.Optional;

/**
 * @author rpuch
 */
public interface UserDao extends MongoCommonDao<User> {
    Optional<User> findByUsername(String username);
}
