package com.cybernation.testservice.controllers;

import io.extremum.authentication.RolesConstants;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoAuthenticationController {
    @RequiresRoles(RolesConstants.CLIENT)
    @GetMapping("/req_client")
    public ResponseEntity<String> requireClientRole() {
        return ResponseEntity.ok("You have client role!");
    }

    @RequiresAuthentication
    @GetMapping("/req_auth")
    public ResponseEntity<String> requireAuth() {
        return ResponseEntity.ok("You are authenticated!");
    }
}
