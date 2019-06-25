package com.cybernation.convert.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.extremum.common.dto.converters","com.cybernation.convert"})
public class ResponseConvertConfig {
}
