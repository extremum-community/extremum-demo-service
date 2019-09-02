package com.cybernation.convert.models;

import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class CommonModelTwoConverter implements ToResponseDtoConverter<CommonModelTwo, TestResponseDto> {
    @Override
    public TestResponseDto convertToResponse(CommonModelTwo commonModelTwo, ConversionConfig conversionConfig) {
        TestResponseDto testResponseDto = new TestResponseDto();
        testResponseDto.setName(commonModelTwo.getName());
        return testResponseDto;
    }

    @Override
    public Class<? extends TestResponseDto> getResponseDtoType() {
        return TestResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return CommonModelTwo.MODEL_NAME;
    }
}
