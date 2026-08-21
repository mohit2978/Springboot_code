package com.example.interceptorandfilter.filter;

import com.example.interceptorandfilter.support.RequestTrace;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(3)
public class TimingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.nanoTime();
        RequestTrace.add(request, "3. TimingFilter");

        filterChain.doFilter(request, response);

        long durationMicros = (System.nanoTime() - startTime) / 1_000;
        response.setHeader("X-Response-Time-Microseconds", Long.toString(durationMicros));
    }
}
