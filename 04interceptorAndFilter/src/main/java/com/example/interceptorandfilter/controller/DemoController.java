package com.example.interceptorandfilter.controller;

import com.example.interceptorandfilter.support.RequestTrace;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public Map<String, Object> hello(HttpServletRequest request) {
        List<String> trace = RequestTrace.get(request);
        trace.add("6. DemoController");

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Hello after three filters and two interceptors");
        response.put("requestId", request.getAttribute("requestId"));
        response.put("executionOrder", List.copyOf(trace));
        return response;
    }
}
