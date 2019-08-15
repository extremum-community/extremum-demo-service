package com.cybernation.testservice.security;

import com.cybernation.testservice.models.auth.User;
import com.cybernation.testservice.services.auth.UserService;
import io.extremum.authentication.common.models.Credentials;
import io.extremum.authentication.common.models.ExternalAuthResult;
import io.extremum.authentication.common.services.ExternalCredentialsAuthenticator;
import io.extremum.sharedmodels.personal.Credential;
import io.extremum.sharedmodels.personal.VerifyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author rpuch
 */
@RequiredArgsConstructor
@Service
public class DemoPasswordAuthenticator implements ExternalCredentialsAuthenticator {
    private final UserService userService;

    @Override
    public Optional<ExternalAuthResult> authenticate(List<Credential> credentialList) {
        Credentials credentials = new Credentials(credentialList);

        if (!credentials.hasCredentialOfType(VerifyType.USERNAME)
                || !credentials.hasCredentialOfType(VerifyType.PASSWORD)) {
            return Optional.empty();
        }

        String username = credentials.requiredValue(VerifyType.USERNAME);
        String passwordToCheck = credentials.requiredValue(VerifyType.PASSWORD);

        return userService.findByUsername(username)
                .filter(user -> passwordMatches(user, passwordToCheck))
                .map(this::userToExternalAuthResult);
    }

    private boolean passwordMatches(User user, String passwordToCheck) {
        // DO NOT DO THIS IN PRODUCTION!!
        return Objects.equals(user.getPassword(), passwordToCheck);
    }

    private ExternalAuthResult userToExternalAuthResult(User user) {
        return new ExternalAuthResult(Optional.ofNullable(user.getIdentityId()), user.getRoles());
    }

    @Override
    public void bindIdentity(List<Credential> credentialList, String identityId) {
        Credentials credentials = new Credentials(credentialList);

        if (!credentials.hasCredentialOfType(VerifyType.USERNAME)
                || !credentials.hasCredentialOfType(VerifyType.PASSWORD)) {
            return;
        }

        String username = credentials.requiredValue(VerifyType.USERNAME);

        userService.changeUserIdentityId(username, identityId);
    }
}
