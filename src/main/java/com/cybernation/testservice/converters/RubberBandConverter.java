package com.cybernation.testservice.converters;

import com.cybernation.testservice.dto.RubberBandRequestDto;
import com.cybernation.testservice.dto.RubberBandResponseDto;
import com.cybernation.testservice.models.elasticsearch.RubberBand;
import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.FromRequestDtoConverter;
import io.extremum.common.dto.converters.ToRequestDtoConverter;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RubberBandConverter implements ToRequestDtoConverter<RubberBand, RubberBandRequestDto>,
        ToResponseDtoConverter<RubberBand, RubberBandResponseDto>, FromRequestDtoConverter<RubberBand, RubberBandRequestDto> {
    @Override
    public RubberBand convertFromRequest(RubberBandRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            RubberBand rubberBand = new RubberBand();
            rubberBand.setName(dto.getName());
            rubberBand.setColor(dto.getColor());
            return rubberBand;
        }
    }

    @Override
    public RubberBandRequestDto convertToRequest(RubberBand rubberBand, ConversionConfig conversionConfig) {
        RubberBandRequestDto dto = new RubberBandRequestDto();
        dto.setName(rubberBand.getName());
        dto.setColor(rubberBand.getColor());
        return dto;
    }

    @Override
    public RubberBandResponseDto convertToResponse(RubberBand rubberBand, ConversionConfig conversionConfig) {
        RubberBandResponseDto dto = new RubberBandResponseDto();
        dto.setId(rubberBand.getUuid());

        dto.setName(rubberBand.getName());
        dto.setColor(rubberBand.getColor());

        return dto;
    }

    @Override
    public Class<? extends RubberBandRequestDto> getRequestDtoType() {
        return RubberBandRequestDto.class;
    }

    @Override
    public Class<? extends RubberBandResponseDto> getResponseDtoType() {
        return RubberBandResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return RubberBand.MODEL_NAME;
    }
}
