package com.cybernation.testservice.models;

import com.extremum.common.descriptor.Descriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestModelDto {
    private String id;
    private Descriptor descriptor;
    private String testId;
}
