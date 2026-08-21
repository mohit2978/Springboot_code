package com.example.interceptorandfilter.support;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public final class RequestTrace {

    private static final String ATTRIBUTE_NAME = "executionTrace";

    private RequestTrace() {
    }

    @SuppressWarnings("unchecked")
    public static List<String> get(HttpServletRequest request) {
        Object existingTrace = request.getAttribute(ATTRIBUTE_NAME);
        if (existingTrace instanceof List<?>) {
            return (List<String>) existingTrace;
        }

        List<String> newTrace = new ArrayList<>();
        request.setAttribute(ATTRIBUTE_NAME, newTrace);
        return newTrace;
    }

    public static void add(HttpServletRequest request, String step) {
        get(request).add(step);
    }
}
