package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.auth.TopSecret;
import com.cybernation.testservice.models.jpa.persistable.Department;
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
public class TopSecretResponseDto extends CommonResponseDto {
    private String secret;

    @Override
    @JsonIgnore
    public String getModel() {
        return TopSecret.MODEL_NAME;
    }
}
