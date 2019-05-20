package com.cybernation.testservice.controllers;

import com.cybernation.testservice.converters.DepartmentConverter;
import com.cybernation.testservice.models.DepartmentRequestDto;
import com.cybernation.testservice.models.DepartmentResponseDto;
import com.cybernation.testservice.services.DepartmentServiceImpl;
import com.extremum.common.dto.converters.ConversionConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jpa")
@RequiredArgsConstructor
public class DepartmentJpaModelController {
    private final DepartmentServiceImpl service;
    private final DepartmentConverter converter;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> store(@RequestBody DepartmentRequestDto dto) {
        try {
            DepartmentResponseDto departmentResponseDto = converter.convertToResponse(service.create(converter.convertFromRequest(dto)), ConversionConfig.builder().build());
            return new ResponseEntity<>(departmentResponseDto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
