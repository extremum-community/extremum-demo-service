package com.cybernation.testservice.models;

import com.extremum.common.models.PostgresBasicModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.common.models.annotation.ModelRequestDto;
import com.extremum.common.models.annotation.ModelResponseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rpuch
 */
@Entity
@Table(name = "fly")
@Getter
@Setter
@ModelName(Fly.MODEL_NAME)
@ModelRequestDto(FlyRequestDto.class)
@ModelResponseDto(FlyResponseDto.class)
public class Fly extends PostgresBasicModel {
    public static final String MODEL_NAME = "Fly";

    private String name;
}
