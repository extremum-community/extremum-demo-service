package com.cybernation.convert.models;

import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class CommonModelOneConverter implements ToResponseDtoConverter<CommonModelOne, CommonResponseDto> {
    @Override
    public CommonResponseDto convertToResponse(CommonModelOne commonModelOne, ConversionConfig conversionConfig) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setName(commonModelOne.getName());
        return commonResponseDto;
    }

    @Override
    public Class<? extends CommonResponseDto> getResponseDtoType() {
        return CommonResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return CommonModelOne.MODEL_NAME;
    }
}
