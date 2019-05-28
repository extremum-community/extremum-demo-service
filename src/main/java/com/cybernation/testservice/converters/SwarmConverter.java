package com.cybernation.testservice.converters;

import com.cybernation.testservice.dto.FlyResponseDto;
import com.cybernation.testservice.dto.SwarmRequestDto;
import com.cybernation.testservice.dto.SwarmResponseDto;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.common.collection.CollectionReference;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.FromRequestDtoConverter;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwarmConverter implements ToRequestDtoConverter<Swarm, SwarmRequestDto>,
        ToResponseDtoConverter<Swarm, SwarmResponseDto>, FromRequestDtoConverter<Swarm, SwarmRequestDto> {
    private final FlyConverter flyConverter;

    @Override
    public Swarm convertFromRequest(SwarmRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Swarm swarm = new Swarm();
            swarm.setName(dto.getName());
            return swarm;
        }
    }

    @Override
    public SwarmRequestDto convertToRequest(Swarm swarm, ConversionConfig conversionConfig) {
        SwarmRequestDto dto = new SwarmRequestDto();
        dto.setName(swarm.getName());
        return dto;
    }

    @Override
    public SwarmResponseDto convertToResponse(Swarm swarm, ConversionConfig conversionConfig) {
        SwarmResponseDto dto = new SwarmResponseDto();
        dto.setId(swarm.getUuid());

        dto.setName(swarm.getName());

        List<FlyResponseDto> flies = swarm.getFlies().stream()
                .map(fly -> flyConverter.convertToResponse(fly, conversionConfig))
                .collect(Collectors.toList());
        dto.setFlies(new CollectionReference<>(flies));
        dto.setCustomFlies(new CollectionReference<>(flies));

        return dto;
    }

    @Override
    public Class<? extends SwarmRequestDto> getRequestDtoType() {
        return SwarmRequestDto.class;
    }

    @Override
    public Class<? extends SwarmResponseDto> getResponseDtoType() {
        return SwarmResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Swarm.MODEL_NAME;
    }
}
