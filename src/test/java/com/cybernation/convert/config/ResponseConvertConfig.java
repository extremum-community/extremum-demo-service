package com.cybernation.convert.config;

import com.extremum.everything.config.listener.ModelClassesInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ComponentScan({"com.extremum.common.dto.converters","com.cybernation.convert"})
public class ResponseConvertConfig {
    @Bean
    public ModelClassesInitializer initializer(){
        return new ModelClassesInitializer(Collections.singletonList("com.cybernation.convert.models"));
    }
}
