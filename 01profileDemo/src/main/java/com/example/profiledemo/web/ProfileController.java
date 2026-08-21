package com.example.profiledemo.web;

import com.example.profiledemo.config.DemoProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    private final DemoProperties properties;
    private final Environment environment;

    public ProfileController(DemoProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    @GetMapping("/profile")
    public Map<String, Object> profile() {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("activeProfiles", activeProfiles.isEmpty() ? List.of("default") : activeProfiles);
        response.put("environment", properties.environment());
        response.put("message", properties.message());
        response.put("databaseUrl", properties.databaseUrl());
        response.put("featureEnabled", properties.featureEnabled());
        return response;
    }
}
