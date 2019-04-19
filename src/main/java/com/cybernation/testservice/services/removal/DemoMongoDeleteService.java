package com.cybernation.testservice.services.removal;

import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.services.DemoMongoService;
import com.extremum.everything.services.RemovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoMongoDeleteService implements RemovalService {
    private final DemoMongoService demoMongoService;

    @Override
    public boolean remove(String id) {
        return demoMongoService.delete(id).getDeleted();
    }

    @Override
    public String getSupportedModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}
