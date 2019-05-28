package com.cybernation.testservice.controllers;

import com.cybernation.testservice.models.DemoElasticModel;
import com.cybernation.testservice.repositories.DemoElasticDao;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/elastic")
public class DemoElasticController {
    private DemoElasticDao dao;
    private ObjectMapper mapper;

    public DemoElasticController(DemoElasticDao dao, ObjectMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @PostMapping("/search/{data}")
    public Response search(@PathVariable String data) {
        return Response.ok(dao.search(data));
    }

    @PostMapping
    public Response create(@RequestBody DemoElasticModel model) throws JsonProcessingException {
        String data = mapper.writeValueAsString(model);

        return Response.ok(dao.persist(model));
    }

    @GetMapping("/{id}")
    public Response getModel(@PathVariable Descriptor id) {
        return Response.ok(dao.findById(id.getInternalId()));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Descriptor id) {
        dao.remove(id.getInternalId());
        return Response.ok();
    }

    @PatchMapping("/{id}")
    public Response patch(@PathVariable Descriptor id) {
        final boolean result = dao.patch(id.getInternalId(), "ctx._source.field2=params.data",
                Collections.singletonMap("data", "patchedValue"));

        return result ? Response.ok() : Response.fail();
    }
}
