package com.example.interceptorandfilter.interceptor;

import com.example.interceptorandfilter.support.RequestTrace;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecondInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestTrace.add(request, "5. SecondInterceptor");
        response.setHeader("X-Second-Interceptor", "executed");
        return true;
    }
}
