package com.cybernation.testservice.config;

import com.cybernation.testservice.security.DemoPasswordAuthenticator;
import com.cybernation.testservice.services.auth.UserService;
import io.extremum.authentication.api.exceptions.AuthenticationException;
import io.extremum.authentication.common.services.ApiKeyVerifier;
import io.extremum.authentication.common.services.ExternalCredentialsAuthenticator;
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

    @Bean
    public ApiKeyVerifier apiKeyVerifier() {
        return new OnlyTestApiKeyAllowed();
    }

    private static class OnlyTestApiKeyAllowed implements ApiKeyVerifier {
        @Override
        public void verifyApiKey(String apiKey) throws AuthenticationException {
            if (!"test".equals(apiKey)) {
                throw new AuthenticationException("Only 'test' apiKey is available in demo!");
            }
        }
    }
}
