package com.example.simplewebsocket.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;


@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        System.out.println("📦 HTTP 요청 정보:");
        System.out.println("▶ Method: " + httpRequest.getMethod());
        System.out.println("▶ URI: " + httpRequest.getRequestURI());
        System.out.println("▶ Protocol: " + httpRequest.getProtocol());
        System.out.println("▶ Remote Addr: " + httpRequest.getRemoteAddr());

        System.out.println("▶ Headers:");
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = httpRequest.getHeader(name);
            System.out.println("   " + name + ": " + value);
        }

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            System.out.println("▶ Session ID: " + session.getId());
        } else {
            System.out.println("▶ Session 없음");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
