package com.cybernation.testservice.config;

import com.cybernation.testservice.security.DemoPasswordAuthenticator;
import com.cybernation.testservice.services.auth.UserService;
import io.extremum.authentication.api.AuthenticationException;
import io.extremum.authentication.common.services.ApiKeyVerifier;
import io.extremum.authentication.common.services.ExternalCredentialsAuthenticator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

    @Bean
    public Object shiroSecurityManagerGlobalizer(SecurityManager securityManager) {
        // FIXME: Here is the problem. Shiro relies on ThreadLocals. Most of this demo
        // application works in a 'predictable same thread' way, with one exception:
        // collection streaming. When collections are streamed, any shiro-related action
        // fails. This seems to only be an issue when Spring's FrameworkServlet publishes
        // 'request processed' event as it obtains the current principal there. Moreover,
        // if anything was written to the stream (that is, if at least one collection element
        // was returned), the response is already committed and the shiro-related error
        // does not disturb request processing. But if there is nothing (the collection is empty
        // or does not exist), the shiro-generated exception is caught and converted to 500.
        // This is quite useless, so we initialize a global shiro security manager to avoid
        // this exception.
        // But actually this is a hack. We need to invent something, probably completely drop
        // thread-locals usage (which probably means dropping Shiro).
        return new Object() {
            @PostConstruct
            public void globalize() {
                SecurityUtils.setSecurityManager(securityManager);
            }
        };
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
