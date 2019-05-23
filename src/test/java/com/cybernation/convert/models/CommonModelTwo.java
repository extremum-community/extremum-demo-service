package com.cybernation.convert.models;

import com.extremum.common.models.Model;
import com.extremum.common.models.annotation.ModelName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ModelName(CommonModelTwo.MODEL_NAME)
@Getter
@AllArgsConstructor
public class CommonModelTwo implements Model {
    public static final String MODEL_NAME = "TWO";

    private String name;
}
