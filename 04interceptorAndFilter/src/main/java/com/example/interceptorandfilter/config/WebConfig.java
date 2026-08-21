package com.example.interceptorandfilter.config;

import com.example.interceptorandfilter.interceptor.FirstInterceptor;
import com.example.interceptorandfilter.interceptor.SecondInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final FirstInterceptor firstInterceptor;
    private final SecondInterceptor secondInterceptor;

    public WebConfig(FirstInterceptor firstInterceptor, SecondInterceptor secondInterceptor) {
        this.firstInterceptor = firstInterceptor;
        this.secondInterceptor = secondInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firstInterceptor)
                .addPathPatterns("/api/**")
                .order(1);
        registry.addInterceptor(secondInterceptor)
                .addPathPatterns("/api/**")
                .order(2);
    }
}
