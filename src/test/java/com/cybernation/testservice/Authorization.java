package com.cybernation.testservice;

import static java.lang.String.format;

/**
 * @author rpuch
 */
public class Authorization {
    public static String bearer(String token) {
        // TODO: leave one of the two options when it becomes clear whether token should
        // contain Bearer prefix or not
        if (token.startsWith("Bearer ")) {
            return token;
        }

        return format("Bearer %s", token);
    }
}
