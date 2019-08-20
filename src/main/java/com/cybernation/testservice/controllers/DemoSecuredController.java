package com.cybernation.testservice.controllers;

import io.extremum.authentication.api.Roles;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class DemoSecuredController {
    @RequiresAuthentication
    @GetMapping("/req_auth")
    public ResponseEntity<String> requireAuth() {
        return ResponseEntity.ok("You are authenticated!");
    }

    @RequiresRoles(Roles.CLIENT)
    @GetMapping("/req_client")
    public ResponseEntity<String> requireClientRole() {
        return ResponseEntity.ok("You have client role!");
    }


    @RequiresRoles(Roles.ANONYMOUS)
    @GetMapping("/req_anonym")
    public ResponseEntity<String> requireAnonymRole() {
        return ResponseEntity.ok("You have anonym role!");
    }

    @RequestMapping(value = "/test_options",method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsTrySecured() {
        return ResponseEntity.ok("OPTIONS not need to be secured!");
    }
}
