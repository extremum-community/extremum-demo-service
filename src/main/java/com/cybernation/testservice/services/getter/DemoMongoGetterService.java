package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.services.DemoMongoService;
import com.extremum.everything.services.GetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoMongoGetterService implements GetterService<DemoMongoModel> {
    private final DemoMongoService demoMongoService;

    @Override
    public DemoMongoModel get(String id) {
        return demoMongoService.get(id);
    }

    @Override
    public String getSupportedModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}
