package com.cybernation.convert.models;

import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class CommonModelOneConverter implements ToResponseDtoConverter<CommonModelOne, TestResponseDto> {
    @Override
    public TestResponseDto convertToResponse(CommonModelOne commonModelOne, ConversionConfig conversionConfig) {
        TestResponseDto testResponseDto = new TestResponseDto();
        testResponseDto.setName(commonModelOne.getName());
        return testResponseDto;
    }

    @Override
    public Class<? extends TestResponseDto> getResponseDtoType() {
        return TestResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return CommonModelOne.MODEL_NAME;
    }
}
