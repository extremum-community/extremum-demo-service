package com.cybernation.testservice.models;

import com.extremum.common.collection.CollectionReference;
import com.extremum.common.collection.conversion.OwnedCollection;
import com.extremum.common.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SwarmRequestDto implements RequestDto {
    private String name;
    @OwnedCollection
    private CollectionReference<FlyResponseDto> flies;
    @OwnedCollection
    private CollectionReference<FlyResponseDto> customFlies;
}
