package com.cybernation.testservice.models.elasticsearch;

import com.extremum.common.models.annotation.ModelName;
import com.extremum.elasticsearch.model.ElasticsearchCommonModel;
import com.extremum.everything.security.NoDataSecurity;
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
@NoDataSecurity
public class RubberBand extends ElasticsearchCommonModel {
    public static final String MODEL_NAME = "RubberBand";

    private String name;
    private String color;
}
