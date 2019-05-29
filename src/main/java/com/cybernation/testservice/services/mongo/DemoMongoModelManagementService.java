package com.cybernation.testservice.services.mongo;

import com.cybernation.testservice.converters.DemoMongoModelConverter;
import com.cybernation.testservice.models.mongo.DemoMongoModel;
import com.cybernation.testservice.dto.DemoMongoModelRequestDto;
import com.cybernation.testservice.dto.DemoMongoModelResponseDto;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.descriptor.service.DescriptorService;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.exceptions.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemoMongoModelManagementService {
    private final DemoMongoServiceImpl service;
    private final DemoMongoModelConverter converter;

    public DemoMongoModelResponseDto create(DemoMongoModelRequestDto dto) {
        return converter.convertToResponse(service.create(converter.convertFromRequest(dto)), ConversionConfig.builder().build());
    }

    public DemoMongoModelResponseDto getById(String externalId) {
        Descriptor descriptor = checkDescriptorByExternalId(externalId);
        DemoMongoModel demoMongoModel = service.get(descriptor.getInternalId());
        return converter.convertToResponse(demoMongoModel, ConversionConfig.builder().build());
    }

    public DemoMongoModelResponseDto updateById(String externalId, DemoMongoModelRequestDto dto) {
        Descriptor descriptor = checkDescriptorByExternalId(externalId);
        Optional<DemoMongoModel> result = Optional.ofNullable(service.get(descriptor.getInternalId()));
        if (result.isPresent()) {
            DemoMongoModel testMongoModel = result.get();
            testMongoModel.setTestId(dto.getTestId());
            return converter.convertToResponse(service.save(testMongoModel), ConversionConfig.builder().build());
        } else {
            return null;
        }
    }


    private Descriptor checkDescriptorByExternalId(String externalId) {
        return DescriptorService.loadByExternalId(externalId).orElseThrow(() -> new CommonException("Not found descriptor for externalId", 404));
    }
}
