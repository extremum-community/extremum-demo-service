package com.cybernation.testservice.controllers;

import com.extremum.everything.controllers.DefaultEverythingEverythingRestController;
import com.extremum.everything.services.management.EverythingEverythingManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoEverythingController extends DefaultEverythingEverythingRestController {
    public DemoEverythingController(EverythingEverythingManagementService evrEvrManagementService) {
        super(evrEvrManagementService);
    }
}
