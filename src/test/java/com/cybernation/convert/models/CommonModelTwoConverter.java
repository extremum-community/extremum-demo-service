package com.cybernation.convert.models;

import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class CommonModelTwoConverter implements ToResponseDtoConverter<CommonModelTwo, CommonResponseDto> {
    @Override
    public CommonResponseDto convertToResponse(CommonModelTwo commonModelTwo, ConversionConfig conversionConfig) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setName(commonModelTwo.getName());
        return commonResponseDto;
    }

    @Override
    public Class<? extends CommonResponseDto> getResponseDtoType() {
        return CommonResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return CommonModelTwo.MODEL_NAME;
    }
}
