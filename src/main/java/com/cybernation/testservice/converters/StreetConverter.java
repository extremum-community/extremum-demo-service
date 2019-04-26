package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.HouseResponseDto;
import com.cybernation.testservice.models.Street;
import com.cybernation.testservice.models.StreetRequestDto;
import com.cybernation.testservice.models.StreetResponseDto;
import com.extremum.common.collection.CollectionReference;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import com.extremum.common.stucts.IdOrObjectStruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreetConverter implements ToRequestDtoConverter<Street, StreetRequestDto>,
        ToResponseDtoConverter<Street, StreetResponseDto> {
    public Street convertFromRequest(StreetRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Street street = new Street();
            street.setName(dto.getName());
            street.setHouses(dto.getHouses());
            return street;
        }
    }

    @Override
    public StreetRequestDto convertToRequest(Street street, ConversionConfig conversionConfig) {
        StreetRequestDto dto = new StreetRequestDto();
        dto.setName(street.getName());
        dto.setHouses(street.getHouses());
        return dto;
    }

    @Override
    public StreetResponseDto convertToResponse(Street street, ConversionConfig conversionConfig) {
        StreetResponseDto dto = new StreetResponseDto();
        dto.setCreated(street.getCreated());
        dto.setId(street.getUuid());
        dto.setModified(street.getModified());

        dto.setName(street.getName());
        // TODO: expand!
        List<IdOrObjectStruct<String, HouseResponseDto>> houses = street.getHouses().stream()
                .map((Function<String, IdOrObjectStruct<String, HouseResponseDto>>) IdOrObjectStruct::new)
                .collect(Collectors.toList());
        dto.setHouses(new CollectionReference<>(houses));

        dto.setVersion(street.getVersion());
        return dto;
    }

    @Override
    public Class<? extends StreetRequestDto> getRequestDtoType() {
        return StreetRequestDto.class;
    }

    @Override
    public Class<? extends StreetResponseDto> getResponseDtoType() {
        return StreetResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Street.MODEL_NAME;
    }
}
