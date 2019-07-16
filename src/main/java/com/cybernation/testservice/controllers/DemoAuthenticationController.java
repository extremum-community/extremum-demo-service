package com.cybernation.testservice.controllers;

import io.extremum.authentication.RolesConstants;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class DemoAuthenticationController {
    private RMapCache<String, String> testMap;

    public DemoAuthenticationController(RedissonClient redissonClient) {
        testMap = redissonClient
                .getMapCache("auth_test");
    }

    @RequiresAuthentication
    @GetMapping("/req_auth")
    public ResponseEntity<String> requireAuth() {
        testMap.put("require_auth", "2", 1, TimeUnit.MINUTES);
        return ResponseEntity.ok("You are authenticated!");
    }

    @RequiresRoles(RolesConstants.CLIENT)
    @GetMapping("/req_client")
    public ResponseEntity<String> requireClientRole() {
        testMap.put("req_client", "1", 1, TimeUnit.MINUTES);
        return ResponseEntity.ok("You have client role!");
    }


    @RequiresRoles(RolesConstants.ANONYMOUS)
    @GetMapping("/req_anonym")
    public ResponseEntity<String> requireAnonymRole() {
        return ResponseEntity.ok("You have anonym role!");
    }
}
