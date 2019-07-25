package com.cybernation.testservice.config;

import com.cybernation.testservice.security.DemoPasswordAuthenticator;
import com.cybernation.testservice.services.auth.UserService;
import io.extremum.authentication.services.ExternalCredentialsAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rpuch
 */
@Configuration
public class SecurityConfiguration {
    @Bean
    public ExternalCredentialsAuthenticator externalCredentialsAuthenticator(UserService userService) {
        return new DemoPasswordAuthenticator(userService);
    }
}
