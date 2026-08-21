package com.example.filters.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public Map<String, Object> hello(HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Hello from the filters demo");
        response.put("filterMessage", request.getAttribute("filterMessage"));
        response.put("requestId", request.getAttribute("requestId"));
        return response;
    }
}
