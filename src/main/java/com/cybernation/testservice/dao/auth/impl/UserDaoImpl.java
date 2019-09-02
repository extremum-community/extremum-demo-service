package com.cybernation.testservice.dao.auth.impl;

import com.cybernation.testservice.dao.auth.UserDao;
import com.cybernation.testservice.models.auth.User;
import io.extremum.mongo.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface UserDaoImpl extends UserDao, SpringDataMongoCommonDao<User> {
}
