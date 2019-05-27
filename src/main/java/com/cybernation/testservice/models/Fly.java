package com.cybernation.testservice.models;

import com.extremum.common.models.PostgresBasicModel;
import com.extremum.common.models.annotation.ModelName;
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
public class Fly extends PostgresBasicModel {
    public static final String MODEL_NAME = "Fly";

    private String name;
}
