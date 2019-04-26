package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.House;
import com.cybernation.testservice.models.HouseRequestDto;
import com.cybernation.testservice.models.HouseResponseDto;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseConverter implements ToRequestDtoConverter<House, HouseRequestDto>,
        ToResponseDtoConverter<House, HouseResponseDto> {
    public House convertFromRequest(HouseRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            House house = new House();
            house.setNumber(dto.getNumber());
            return house;
        }
    }

    @Override
    public HouseRequestDto convertToRequest(House house, ConversionConfig conversionConfig) {
        HouseRequestDto dto = new HouseRequestDto();
        dto.setNumber(house.getNumber());
        return dto;
    }

    @Override
    public HouseResponseDto convertToResponse(House house, ConversionConfig conversionConfig) {
        HouseResponseDto dto = new HouseResponseDto();
        dto.setCreated(house.getCreated());
        dto.setId(house.getUuid());
        dto.setModified(house.getModified());
        dto.setNumber(house.getNumber());
        dto.setVersion(house.getVersion());
        return dto;
    }

    @Override
    public Class<? extends HouseRequestDto> getRequestDtoType() {
        return HouseRequestDto.class;
    }

    @Override
    public Class<? extends HouseResponseDto> getResponseDtoType() {
        return HouseResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return House.MODEL_NAME;
    }
}
