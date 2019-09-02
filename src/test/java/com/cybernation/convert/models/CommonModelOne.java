package com.cybernation.convert.models;

import io.extremum.common.model.Model;
import io.extremum.common.model.annotation.ModelName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ModelName(CommonModelOne.MODEL_NAME)
@Getter
@AllArgsConstructor
public class CommonModelOne implements Model {
    public static final String MODEL_NAME = "ONE";

    private String name;
}
