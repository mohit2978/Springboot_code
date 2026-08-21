package com.example.interceptors.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CustomRequestInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Interceptor preHandle: {} {}", request.getMethod(), request.getRequestURI());
        request.setAttribute("interceptorMessage", "Request processed by CustomRequestInterceptor");
        response.setHeader("X-Custom-Interceptor", "executed");
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception) {
        log.info("Interceptor afterCompletion: status={}", response.getStatus());
    }
}
