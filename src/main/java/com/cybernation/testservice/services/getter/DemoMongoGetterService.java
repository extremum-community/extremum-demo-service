package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.DemoMongoModel;
import com.cybernation.testservice.services.DemoMongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoMongoGetterService {
    private final DemoMongoService demoMongoService;

    public DemoMongoModel get(String id) {
        return demoMongoService.get(id);
    }
}
