package com.cybernation.testservice.models;

import com.extremum.common.models.ElasticCommonModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoElasticModel extends ElasticCommonModel {
    private String field1;
    private String field2;

    @Override
    public String getModelName() {
        return ElasticCommonModel.class.getSimpleName();
    }
}
