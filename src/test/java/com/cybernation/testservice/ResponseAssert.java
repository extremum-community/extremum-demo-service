package com.cybernation.testservice;

import io.extremum.common.response.Response;
import io.extremum.common.response.ResponseStatusEnum;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author rpuch
 */
class ResponseAssert {


    private ResponseAssert() {
    }

    static Consumer<Response> isSuccessful() {
        return response -> assertAll(
                () -> assertThat("Response status", response.getStatus(), is(ResponseStatusEnum.OK)),
                () -> assertThat("Response code", response.getCode(), is(200)),
                () -> assertThat(response, hasNoAlerts())
        );
    }

    private static Matcher<Response> hasNoAlerts() {
        return new CustomTypeSafeMatcher<Response>("Response without alerts") {
            @Override
            protected boolean matchesSafely(Response item) {
                return item.getAlerts() == null || item.getAlerts().isEmpty();
            }
        };
    }

    static Consumer<Response> is403() {
        return response -> assertAll(
                () -> assertThat("Response status", response.getStatus(), is(ResponseStatusEnum.FAIL)),
                () -> assertThat("Response code", response.getCode(), is(403))
        );
    }
}
