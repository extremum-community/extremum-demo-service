package com.cybernation.testservice;

/**
 * May be used to add a prefix to all 'our' URLs centrally if
 * the app specific prefix changes.
 *
 * @author rpuch
 */
public class UrlPrefix {
    public static String prefix(String url) {
        return url;
    }

    private UrlPrefix() {}
}
