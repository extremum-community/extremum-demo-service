package com.cybernation.convert.models;

import io.extremum.common.model.annotation.ModelName;
import io.extremum.sharedmodels.basic.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ModelName(CommonModelTwo.MODEL_NAME)
@Getter
@AllArgsConstructor
public class CommonModelTwo implements Model {
    public static final String MODEL_NAME = "TWO";

    private String name;
}
