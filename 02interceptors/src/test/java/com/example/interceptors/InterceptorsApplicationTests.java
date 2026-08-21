package com.example.interceptors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InterceptorsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void interceptorRunsForApiEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Custom-Interceptor", "executed"))
                .andExpect(jsonPath("$.interceptorMessage")
                        .value("Request processed by CustomRequestInterceptor"));
    }

    @Test
    void interceptorDoesNotRunForPublicEndpoint() throws Exception {
        mockMvc.perform(get("/public/hello"))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("X-Custom-Interceptor"));
    }
}
