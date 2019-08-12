package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.cybernation.testservice.dto.FlyRequestDto;
import com.cybernation.testservice.dto.FlyResponseDto;
import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.FromRequestDtoConverter;
import io.extremum.common.dto.converters.ToRequestDtoConverter;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlyConverter implements ToRequestDtoConverter<Fly, FlyRequestDto>,
        ToResponseDtoConverter<Fly, FlyResponseDto>, FromRequestDtoConverter<Fly, FlyRequestDto> {
    @Override
    public Fly convertFromRequest(FlyRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Fly fly = new Fly();
            fly.setName(dto.getName());
            return fly;
        }
    }

    @Override
    public FlyRequestDto convertToRequest(Fly fly, ConversionConfig conversionConfig) {
        FlyRequestDto dto = new FlyRequestDto();
        dto.setName(fly.getName());
        return dto;
    }

    @Override
    public FlyResponseDto convertToResponse(Fly fly, ConversionConfig conversionConfig) {
        FlyResponseDto dto = new FlyResponseDto();
        dto.setId(fly.getUuid());

        dto.setName(fly.getName());

        return dto;
    }

    @Override
    public Class<? extends FlyRequestDto> getRequestDtoType() {
        return FlyRequestDto.class;
    }

    @Override
    public Class<? extends FlyResponseDto> getResponseDtoType() {
        return FlyResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Fly.MODEL_NAME;
    }
}
