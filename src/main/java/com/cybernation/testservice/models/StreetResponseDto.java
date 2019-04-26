package com.cybernation.testservice.models;

import com.extremum.common.collection.CollectionReference;
import com.extremum.common.collection.conversion.MongoEmbeddedCollection;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.extremum.common.stucts.IdOrObjectStruct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class StreetResponseDto implements ResponseDto {
    private Descriptor id;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    private String name;
    @MongoEmbeddedCollection
    private CollectionReference<IdOrObjectStruct<String, HouseResponseDto>> houses;

    @Override
    public Descriptor getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public ZonedDateTime getModified() {
        return modified;
    }

    @Override
    @JsonIgnore
    public String getModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}
