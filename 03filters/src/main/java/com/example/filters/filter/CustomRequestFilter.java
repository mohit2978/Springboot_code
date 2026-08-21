package com.example.filters.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomRequestFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);
        request.setAttribute("filterMessage", "Request processed by CustomRequestFilter");
        response.setHeader("X-Request-Id", requestId);
        response.setHeader("X-Custom-Filter", "executed");

        log.info("Filter before controller: {} {} requestId={}",
                request.getMethod(), request.getRequestURI(), requestId);
        filterChain.doFilter(request, response);
        log.info("Filter after controller: status={} requestId={}", response.getStatus(), requestId);
    }
}
