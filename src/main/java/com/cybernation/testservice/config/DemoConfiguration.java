package com.cybernation.testservice.config;

import io.extremum.common.collection.conversion.CollectionUrls;
import io.extremum.common.urls.ApplicationUrls;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DemoConfiguration {
    private final ApplicationUrls applicationUrls;

    @Bean
    public CollectionUrls collectionUrls() {
        return new CollectionUrlsUnderPrefixV1();
    }

    private class CollectionUrlsUnderPrefixV1 implements CollectionUrls {
        @Override
        public String collectionUrl(String collectionDescriptorExternalId) {
            String uri = "/v1/" + collectionDescriptorExternalId;
            return applicationUrls.createExternalUrl(uri);
        }
    }
}
