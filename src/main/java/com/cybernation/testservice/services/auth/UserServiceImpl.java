package com.cybernation.testservice.services.auth;

import com.cybernation.testservice.dao.auth.UserDao;
import com.cybernation.testservice.models.auth.User;
import io.extremum.common.service.impl.MongoCommonServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author rpuch
 */
@Service
public class UserServiceImpl extends MongoCommonServiceImpl<User> implements UserService {
    private final UserDao dao;

    public UserServiceImpl(UserDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public void changeUserIdentityId(String username, String identityId) {
        findByUsername(username).ifPresent(user -> {
            user.setIdentityId(identityId);
            dao.save(user);
        });
    }
}
