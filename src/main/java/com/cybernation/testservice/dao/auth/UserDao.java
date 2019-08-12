package com.cybernation.testservice.dao.auth;

import com.cybernation.testservice.models.auth.User;
import io.extremum.common.dao.MongoCommonDao;

import java.util.Optional;

/**
 * @author rpuch
 */
public interface UserDao extends MongoCommonDao<User> {
    Optional<User> findByUsername(String username);
}
