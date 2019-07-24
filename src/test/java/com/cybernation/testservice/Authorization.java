package com.cybernation.testservice;

import static java.lang.String.format;

/**
 * @author rpuch
 */
public class Authorization {
    public static String bearer(String token) {
        return format("Bearer %s", token);
    }
}
