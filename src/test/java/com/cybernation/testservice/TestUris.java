package com.cybernation.testservice;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

class TestUris {
    @NotNull
    static URI buildUriWithEncodedQueryString(Map<String, String> queryParams,
                                               String housesCollectionUrl) throws URISyntaxException {
        String encodedQueryParams = queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + urlEncode(entry.getValue()))
                .collect(Collectors.joining("&"));
        return new URI(housesCollectionUrl + "?" + encodedQueryParams);
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private TestUris() {
    }
}
