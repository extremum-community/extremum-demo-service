package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoMongoModelConverter implements ToRequestDtoConverter<DemoMongoModel, DemoMongoModelRequestDto>, ToResponseDtoConverter<DemoMongoModel, DemoMongoModelResponseDto> {
    public DemoMongoModel convertFromRequest(DemoMongoModelRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            DemoMongoModel mongoModel = new DemoMongoModel();
            mongoModel.setTestId(dto.getTestId());
            return mongoModel;
        }
    }

    @Override
    public DemoMongoModelRequestDto convertToRequest(DemoMongoModel demoMongoModel, ConversionConfig conversionConfig) {
        DemoMongoModelRequestDto dto = new DemoMongoModelRequestDto();
        dto.setTestId(demoMongoModel.getTestId());
        return dto;
    }

    @Override
    public DemoMongoModelResponseDto convertToResponse(DemoMongoModel demoMongoModel, ConversionConfig conversionConfig) {
        DemoMongoModelResponseDto dto = new DemoMongoModelResponseDto();
        dto.setCreated(demoMongoModel.getCreated());
        dto.setId(demoMongoModel.getUuid());
        dto.setModified(demoMongoModel.getModified());
        dto.setTestId(demoMongoModel.getTestId());
        dto.setVersion(demoMongoModel.getVersion());
        return dto;
    }

    @Override
    public Class<? extends DemoMongoModelRequestDto> getRequestDtoType() {
        return DemoMongoModelRequestDto.class;
    }

    @Override
    public Class<? extends DemoMongoModelResponseDto> getResponseDtoType() {
        return DemoMongoModelResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}
