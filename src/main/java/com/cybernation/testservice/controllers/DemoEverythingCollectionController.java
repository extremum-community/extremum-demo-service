package com.cybernation.testservice.controllers;

import io.extremum.everything.controllers.DefaultEverythingEverythingCollectionRestController;
import io.extremum.everything.services.management.EverythingCollectionManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoEverythingCollectionController extends DefaultEverythingEverythingCollectionRestController {
    public DemoEverythingCollectionController(EverythingCollectionManagementService collectionManagementService) {
        super(collectionManagementService);
    }
}
