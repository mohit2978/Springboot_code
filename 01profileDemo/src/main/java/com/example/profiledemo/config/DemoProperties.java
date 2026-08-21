package com.example.profiledemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo")
public record DemoProperties(
        String environment,
        String message,
        String databaseUrl,
        boolean featureEnabled) {
}
