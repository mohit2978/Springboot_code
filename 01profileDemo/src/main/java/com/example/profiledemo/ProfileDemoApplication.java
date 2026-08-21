package com.example.profiledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProfileDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileDemoApplication.class, args);
    }
}
