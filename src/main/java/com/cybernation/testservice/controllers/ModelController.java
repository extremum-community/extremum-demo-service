package com.cybernation.testservice.controllers;

import com.cybernation.testservice.models.TestModelDto;
import com.cybernation.testservice.models.TestMongoModel;
import com.cybernation.testservice.services.ModelService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<TestModelDto> store(@RequestParam String id) {
        try {
            TestMongoModel result = modelService.create(new TestMongoModel(id));
            return new ResponseEntity<>(new TestModelDto(result.getId().toString(), result.getUuid(), result.getTestId()), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestModelDto> getById(@PathVariable String id) {
        try {
            TestMongoModel result = modelService.get(id);
            return new ResponseEntity<>(new TestModelDto(result.getId().toString(), result.getUuid(), result.getTestId()), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TestModelDto> updateById(@PathVariable String id, @RequestParam(name = "id") String testId) {
        try {
            Optional<TestMongoModel> result = Optional.ofNullable(modelService.get(id));
            if (result.isPresent()) {
                TestMongoModel testMongoModel = result.get();
                testMongoModel.setTestId(testId);
                testMongoModel.setId(new ObjectId(id));
                TestMongoModel updatedModel = modelService.save(testMongoModel);
                return new ResponseEntity<>(new TestModelDto(updatedModel.getId().toString(), updatedModel.getUuid(), updatedModel.getTestId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
