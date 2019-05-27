package com.cybernation.testservice.models;

import com.extremum.common.collection.CollectionReference;
import com.extremum.common.collection.conversion.OwnedCollection;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class SwarmResponseDto implements ResponseDto {
    private Descriptor id;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

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
