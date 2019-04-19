package com.cybernation.testservice.services;

import com.cybernation.testservice.converters.DemoMongoModelConverter;
import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.cybernation.testservice.services.getter.DemoMongoGetterService;
import com.cybernation.testservice.services.patcher.DemoMongoPatcherService;
import com.cybernation.testservice.services.removal.DemoMongoDeleteService;
import com.extremum.common.dto.converters.ConversionConfig;
import org.bson.types.ObjectId;
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

    public DemoMongoModelResponseDto getById(String id) {
        return converter.convertToResponse(service.get(id), ConversionConfig.builder().build());
    }

    public DemoMongoModelResponseDto updateById(String id, String testId) {
        Optional<DemoMongoModel> result = Optional.ofNullable(service.get(id));
        if (result.isPresent()) {
            DemoMongoModel testMongoModel = result.get();
            testMongoModel.setTestId(testId);
            testMongoModel.setId(new ObjectId(id));
            return converter.convertToResponse(service.save(testMongoModel), ConversionConfig.builder().build());
        } else {
            return null;
        }
    }

}
