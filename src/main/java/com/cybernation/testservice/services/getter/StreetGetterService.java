package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.Street;
import com.cybernation.testservice.services.StreetService;
import com.extremum.everything.services.GetterService;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class StreetGetterService implements GetterService<Street> {
    private final StreetService streetService;

    public StreetGetterService(StreetService streetService) {
        this.streetService = streetService;
    }

    @Override
    public Street get(String id) {
        return streetService.get(id);
    }

    @Override
    public String getSupportedModel() {
        return Street.MODEL_NAME;
    }
}
