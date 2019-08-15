package com.cybernation.testservice.models.elasticsearch;

import io.extremum.authentication.api.Roles;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.elasticsearch.model.ElasticsearchCommonModel;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author rpuch
 */
@Document(indexName = "rubber_band")
@ModelName(RubberBand.MODEL_NAME)
@Getter
@Setter
@ExtremumRequiredRoles(defaultAccess = Roles.ANONYMOUS)
@NoDataSecurity
public class RubberBand extends ElasticsearchCommonModel {
    public static final String MODEL_NAME = "RubberBand";

    private String name;
    private String color;
}
