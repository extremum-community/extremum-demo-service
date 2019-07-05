package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.extremum.common.collection.CollectionReference;
import com.extremum.common.collection.conversion.OwnedCollection;
import com.extremum.sharedmodels.fundamental.CommonResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SwarmResponseDto extends CommonResponseDto {
    private String name;
    @OwnedCollection
    private CollectionReference<FlyResponseDto> flies;
    @OwnedCollection
    private CollectionReference<FlyResponseDto> customFlies;

    @Override
    @JsonIgnore
    public String getModel() {
        return Swarm.MODEL_NAME;
    }
}
