package com.example.interceptors.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/api/hello")
    public Map<String, Object> interceptedHello(HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Hello from the intercepted endpoint");
        response.put("interceptorMessage", request.getAttribute("interceptorMessage"));
        return response;
    }

    @GetMapping("/public/hello")
    public Map<String, String> publicHello() {
        return Map.of("message", "Hello from the public endpoint");
    }
}
