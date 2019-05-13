package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.House;
import com.cybernation.testservice.services.HouseService;
import com.extremum.everything.services.GetterService;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class HouseGetterService implements GetterService<House> {
    private final HouseService houseService;

    public HouseGetterService(HouseService houseService) {
        this.houseService = houseService;
    }

    @Override
    public House get(String id) {
        return houseService.get(id);
    }

    @Override
    public String getSupportedModel() {
        return House.MODEL_NAME;
    }
}
