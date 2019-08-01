package com.cybernation.testservice.models.elasticsearch;

import com.extremum.common.models.annotation.ModelName;
import com.extremum.elasticsearch.model.ElasticsearchCommonModel;
import com.extremum.security.Access;
import com.extremum.security.ExtremumRequiredRoles;
import com.extremum.security.NoDataSecurity;
import io.extremum.authentication.api.RolesConstants;
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
@ExtremumRequiredRoles(defaultAccess = @Access(RolesConstants.ANONYMOUS))
@NoDataSecurity
public class RubberBand extends ElasticsearchCommonModel {
    public static final String MODEL_NAME = "RubberBand";

    private String name;
    private String color;
}
