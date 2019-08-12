package com.cybernation.testservice.converters;

import com.cybernation.testservice.dto.TopSecretRequestDto;
import com.cybernation.testservice.dto.TopSecretResponseDto;
import com.cybernation.testservice.models.auth.TopSecret;
import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.FromRequestDtoConverter;
import io.extremum.common.dto.converters.ToRequestDtoConverter;
import io.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopSecretConverter implements ToRequestDtoConverter<TopSecret, TopSecretRequestDto>,
        ToResponseDtoConverter<TopSecret, TopSecretResponseDto>,
        FromRequestDtoConverter<TopSecret, TopSecretRequestDto> {
    private final EmployeeConverter employeeConverter;

    @Override
    public TopSecret convertFromRequest(TopSecretRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            TopSecret topSecret = new TopSecret();
            topSecret.setSecret(dto.getSecret());
            return topSecret;
        }
    }

    @Override
    public TopSecretRequestDto convertToRequest(TopSecret topSecret, ConversionConfig conversionConfig) {
        TopSecretRequestDto dto = new TopSecretRequestDto();
        dto.setSecret(topSecret.getSecret());
        return dto;
    }

    @Override
    public TopSecretResponseDto convertToResponse(TopSecret topSecret, ConversionConfig conversionConfig) {
        TopSecretResponseDto dto = new TopSecretResponseDto();
        dto.setCreated(topSecret.getCreated());
        dto.setId(topSecret.getUuid());
        dto.setModified(topSecret.getModified());

        dto.setSecret(topSecret.getSecret());

        dto.setVersion(topSecret.getVersion());
        return dto;
    }

    @Override
    public Class<? extends TopSecretRequestDto> getRequestDtoType() {
        return TopSecretRequestDto.class;
    }

    @Override
    public Class<? extends TopSecretResponseDto> getResponseDtoType() {
        return TopSecretResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return TopSecret.MODEL_NAME;
    }
}
