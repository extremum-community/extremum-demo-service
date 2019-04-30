package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;

/**
 * @author rpuch
 */
@Entity(House.MODEL_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class House extends MongoCommonModel {
    public static final String MODEL_NAME = "House";

    private String number;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    public enum FIELDS {
        number
    }
}
