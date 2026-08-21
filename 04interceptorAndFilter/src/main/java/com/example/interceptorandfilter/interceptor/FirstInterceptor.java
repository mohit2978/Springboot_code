package com.example.interceptorandfilter.interceptor;

import com.example.interceptorandfilter.support.RequestTrace;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FirstInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestTrace.add(request, "4. FirstInterceptor");
        response.setHeader("X-First-Interceptor", "executed");
        return true;
    }
}
