package com.example.profiledemo;

import com.example.profiledemo.config.DemoProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class ProfileDemoApplicationTests {

    @Autowired
    private DemoProperties properties;

    @Test
    void devProfileLoadsDevValues() {
        assertThat(properties.environment()).isEqualTo("development");
        assertThat(properties.message()).isEqualTo("Hello from the DEV profile");
        assertThat(properties.databaseUrl()).isEqualTo("jdbc:h2:mem:devdb");
        assertThat(properties.featureEnabled()).isTrue();
    }
}
