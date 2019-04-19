package com.cybernation.testservice.services.patcher;

import com.cybernation.testservice.converters.DemoMongoModelConverter;
import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.services.DemoMongoService;
import com.extremum.common.dto.converters.services.DtoConversionService;
import com.extremum.everything.destroyer.EmptyFieldDestroyer;
import com.extremum.everything.services.AbstractPatcherService;
import com.extremum.everything.services.MergeServiceFieldMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DemoMongoPatcherService extends AbstractPatcherService<DemoMongoModel> implements MergeServiceFieldMixin {
    private final DemoMongoService service;
    private final DemoMongoModelConverter converter;

    public DemoMongoPatcherService(DtoConversionService dtoConversionService, ObjectMapper jsonMapper, EmptyFieldDestroyer emptyFieldDestroyer, DemoMongoService service, DemoMongoModelConverter converter) {
        super(dtoConversionService, jsonMapper, emptyFieldDestroyer);
        this.service = service;
        this.converter = converter;
    }

    @Override
    protected DemoMongoModel persist(PatchPersistenceContext<DemoMongoModel> context) {
        DemoMongoModelRequestDto requestDto = (DemoMongoModelRequestDto) context.getRequestDto();
        DemoMongoModel mongoModel = converter.convertFromRequest(requestDto);
        mergeServiceFields(context.getOriginModel(), mongoModel);
        return service.save(mongoModel);
    }

    @Override
    protected DemoMongoModel findById(String id) {
        return service.get(id);
    }

    @Override
    public String getSupportedModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}
