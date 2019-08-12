package com.cybernation.testservice.dto;

import io.extremum.common.collection.conversion.OwnedCollection;
import io.extremum.sharedmodels.dto.RequestDto;
import io.extremum.sharedmodels.fundamental.CollectionReference;
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
