package com.cybernation.testservice.controllers;

import com.extremum.everything.controllers.DefaultEverythingEverythingCollectionRestController;
import com.extremum.everything.services.management.EverythingCollectionManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoEverythingCollectionController extends DefaultEverythingEverythingCollectionRestController {
    public DemoEverythingCollectionController(EverythingCollectionManagementService collectionManagementService) {
        super(collectionManagementService);
    }
}
