package com.cybernation.testservice.services;

import com.cybernation.testservice.converters.DemoMongoModelConverter;
import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.cybernation.testservice.services.getter.DemoMongoGetterService;
import com.cybernation.testservice.services.patcher.DemoMongoPatcherService;
import com.cybernation.testservice.services.removal.DemoMongoDeleteService;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.descriptor.service.DescriptorService;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.exceptions.CommonException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoMongoModelManagementService {
    private final DemoMongoModelServiceImpl service;
    private final DemoMongoGetterService getterService;
    private final DemoMongoPatcherService patcherService;
    private final DemoMongoDeleteService deleteService;
    private final DemoMongoModelConverter converter;

    public DemoMongoModelManagementService(DemoMongoModelServiceImpl service, DemoMongoGetterService getterService, DemoMongoPatcherService patcherService, DemoMongoDeleteService deleteService, DemoMongoModelConverter converter) {
        this.service = service;
        this.getterService = getterService;
        this.patcherService = patcherService;
        this.deleteService = deleteService;
        this.converter = converter;
    }

    public DemoMongoModelResponseDto create(DemoMongoModelRequestDto dto) {
        return converter.convertToResponse(service.create(converter.convertFromRequest(dto)), ConversionConfig.builder().build());
    }

    public DemoMongoModelResponseDto getById(String externalId) {
        Descriptor descriptor = checkDescriptorByExternalId(externalId);
        DemoMongoModel demoMongoModel = service.get(descriptor.getInternalId());
        return converter.convertToResponse(demoMongoModel, ConversionConfig.builder().build());
    }

    public DemoMongoModelResponseDto updateById(String externalId, String testId) {
        Descriptor descriptor = checkDescriptorByExternalId(externalId);
        Optional<DemoMongoModel> result = Optional.ofNullable(service.get(descriptor.getInternalId()));
        if (result.isPresent()) {
            DemoMongoModel testMongoModel = result.get();
            testMongoModel.setTestId(testId);
            return converter.convertToResponse(service.save(testMongoModel), ConversionConfig.builder().build());
        } else {
            return null;
        }
    }


    private Descriptor checkDescriptorByExternalId(String externalId) {
        return DescriptorService.loadByExternalId(externalId).orElseThrow(() -> new CommonException("Not found descriptor for externalId", 404));
    }
}
