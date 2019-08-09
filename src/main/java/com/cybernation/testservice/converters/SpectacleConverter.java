package com.cybernation.testservice.converters;

import com.cybernation.testservice.dto.SpectacleRequestDto;
import com.cybernation.testservice.dto.SpectacleResponseDto;
import com.cybernation.testservice.models.watch.Spectacle;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.FromRequestDtoConverter;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpectacleConverter implements ToRequestDtoConverter<Spectacle, SpectacleRequestDto>,
        ToResponseDtoConverter<Spectacle, SpectacleResponseDto>, FromRequestDtoConverter<Spectacle, SpectacleRequestDto> {
    @Override
    public Spectacle convertFromRequest(SpectacleRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Spectacle spectacle = new Spectacle();
            spectacle.setName(dto.getName());
            return spectacle;
        }
    }

    @Override
    public SpectacleRequestDto convertToRequest(Spectacle spectacle, ConversionConfig conversionConfig) {
        SpectacleRequestDto dto = new SpectacleRequestDto();
        dto.setName(spectacle.getName());
        return dto;
    }

    @Override
    public SpectacleResponseDto convertToResponse(Spectacle spectacle, ConversionConfig conversionConfig) {
        SpectacleResponseDto dto = new SpectacleResponseDto();
        dto.setId(spectacle.getUuid());

        dto.setName(spectacle.getName());

        return dto;
    }

    @Override
    public Class<? extends SpectacleRequestDto> getRequestDtoType() {
        return SpectacleRequestDto.class;
    }

    @Override
    public Class<? extends SpectacleResponseDto> getResponseDtoType() {
        return SpectacleResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Spectacle.MODEL_NAME;
    }
}
