package com.cybernation.testservice.configuration;

import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.descriptor.dao.DescriptorDao;
import com.extremum.common.descriptor.serde.mongo.DescriptorDecodeTransformer;
import com.extremum.common.descriptor.serde.mongo.DescriptorEncodeTransformer;
import com.extremum.common.descriptor.service.DescriptorService;
import org.bson.BSON;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DescriptorServiceConfigurator {
    private final DescriptorDao descriptorDao;

    public DescriptorServiceConfigurator(DescriptorDao descriptorDao) {
        this.descriptorDao = descriptorDao;
    }

    @PostConstruct
    public void init() {
        DescriptorService.setDescriptorDao(descriptorDao);
        BSON.addDecodingHook(Descriptor.class, new DescriptorDecodeTransformer());
        BSON.addEncodingHook(Descriptor.class, new DescriptorEncodeTransformer());
    }
}
