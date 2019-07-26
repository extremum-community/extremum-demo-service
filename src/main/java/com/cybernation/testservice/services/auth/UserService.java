package com.cybernation.testservice.services.auth;

import com.cybernation.testservice.models.auth.User;
import com.extremum.common.service.CommonService;

import java.util.Optional;

/**
 * @author rpuch
 */
public interface UserService extends CommonService<User> {
    Optional<User> findByUsername(String username);

    void changeUserIdentityId(String username, String identityId);
}
