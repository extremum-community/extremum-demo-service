package com.cybernation;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AntMatcherTest {
    private final PatternMatcher matcher = new AntPathMatcher();
    private final String mongoPattern = "/api/mongo/**";
    private final String firstLevelPattern = "/*";

    @Test
    void testMongoPattern() {
        assertTrue(matcher.matches(mongoPattern, "/api/mongo"));
        assertTrue(matcher.matches(mongoPattern, "/api/mongo/"));
        assertTrue(matcher.matches(mongoPattern, "/api/mongo/12"));
    }

    @Test
    void testFirstLevelPattern() {
        assertTrue(matcher.matches(firstLevelPattern, "/" + UUID.randomUUID().toString()));
        assertTrue(matcher.matches(firstLevelPattern, "/1212"));
        assertFalse(matcher.matches(firstLevelPattern, "/1212/"));
    }
}
