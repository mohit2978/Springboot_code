package com.example.interceptorandfilter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InterceptorAndFilterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void filtersAndInterceptorsRunInConfiguredOrder() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Logging-Filter", "executed"))
                .andExpect(header().string("X-Request-Id", not(blankOrNullString())))
                .andExpect(header().string("X-Response-Time-Microseconds", not(blankOrNullString())))
                .andExpect(header().string("X-First-Interceptor", "executed"))
                .andExpect(header().string("X-Second-Interceptor", "executed"))
                .andExpect(jsonPath("$.executionOrder[0]").value("1. LoggingFilter"))
                .andExpect(jsonPath("$.executionOrder[1]").value("2. RequestIdFilter"))
                .andExpect(jsonPath("$.executionOrder[2]").value("3. TimingFilter"))
                .andExpect(jsonPath("$.executionOrder[3]").value("4. FirstInterceptor"))
                .andExpect(jsonPath("$.executionOrder[4]").value("5. SecondInterceptor"))
                .andExpect(jsonPath("$.executionOrder[5]").value("6. DemoController"));
    }
}
