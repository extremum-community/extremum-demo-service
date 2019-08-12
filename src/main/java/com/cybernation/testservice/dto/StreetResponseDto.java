package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.mongo.Street;
import io.extremum.common.collection.conversion.OwnedCollection;
import io.extremum.sharedmodels.fundamental.CollectionReference;
import io.extremum.sharedmodels.fundamental.CommonResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class StreetResponseDto extends CommonResponseDto {
    private String name;
    @OwnedCollection
    private CollectionReference<HouseResponseDto> houses;

    @Override
    @JsonIgnore
    public String getModel() {
        return Street.MODEL_NAME;
    }
}
