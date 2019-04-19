package com.cybernation.testservice.configuration;

import com.extremum.common.ExtremumCommonConfiguration;
import com.extremum.everything.config.EverythingEverythingConfiguration;
import com.extremum.everything.destroyer.EmptyFieldDestroyer;
import com.extremum.everything.destroyer.EmptyFieldDestroyerConfig;
import com.extremum.everything.destroyer.PublicEmptyFieldDestroyer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;


@Configuration
@Import({ExtremumCommonConfiguration.class, EverythingEverythingConfiguration.class})
public class DescriptorConfiguration {
    @Bean
    public EmptyFieldDestroyer emptyFieldDestroyer() {
        EmptyFieldDestroyerConfig config = new EmptyFieldDestroyerConfig();
        config.setAnalyzablePackagePrefixes(Collections.singletonList("com.cybernation.testservice"));
        return new PublicEmptyFieldDestroyer(config);
    }
}
