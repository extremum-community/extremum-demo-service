package com.cybernation.testservice.models.auth;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rpuch
 */
@ModelName("User")
@Document("users")
@Getter
@Setter
public class User extends MongoCommonModel {
    @Indexed
    private String username;
    // the password is stored plain for simplicity, DON'T DO THIS IN PRODUCTION!!!
    private String password;
    private Set<String> roles = new HashSet<>();
    private String identityId;
}