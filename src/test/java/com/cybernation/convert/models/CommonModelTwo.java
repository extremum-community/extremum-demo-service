package com.cybernation.convert.models;

import io.extremum.common.model.Model;
import io.extremum.common.model.annotation.ModelName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ModelName(CommonModelTwo.MODEL_NAME)
@Getter
@AllArgsConstructor
public class CommonModelTwo implements Model {
    public static final String MODEL_NAME = "TWO";

    private String name;
}
