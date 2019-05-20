package com.cybernation.testservice.controllers;

import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.cybernation.testservice.services.DemoMongoModelManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/mongo")
public class DemoMongoModelController {
    private final DemoMongoModelManagementService managementService;

    public DemoMongoModelController(DemoMongoModelManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<DemoMongoModelResponseDto> store(@RequestBody DemoMongoModelRequestDto dto) {
        try {
            return new ResponseEntity<>(managementService.create(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemoMongoModelResponseDto> getById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(managementService.getById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DemoMongoModelResponseDto> updateById(@PathVariable String id, @RequestBody DemoMongoModelRequestDto dto) {
        try {
            Optional<DemoMongoModelResponseDto> demoMongoModelResponseDto = Optional.ofNullable(managementService.updateById(id, dto));
            return demoMongoModelResponseDto
                    .map(mongoModelResponseDto -> new ResponseEntity<>(mongoModelResponseDto, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
