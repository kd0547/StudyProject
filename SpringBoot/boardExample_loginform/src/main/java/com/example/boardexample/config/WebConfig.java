package com.example.boardexample.config;

import com.example.boardexample.interceptor.BoardViewInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //인터셉터 등록
        registry.addInterceptor(new BoardViewInterceptor())
                .addPathPatterns("/post/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
