package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.everything.ElementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rpuch
 */
@Entity(value = Street.MODEL_NAME, noClassnameStored = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Street extends MongoCommonModel {
    public static final String MODEL_NAME = "Street";

    private String name;
    @ElementType(House.class)
    private List<String> houses = new ArrayList<>();

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    public enum FIELDS {
        name, houses
    }
}
