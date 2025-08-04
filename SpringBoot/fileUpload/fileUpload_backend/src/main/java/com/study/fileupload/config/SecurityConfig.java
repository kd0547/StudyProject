package com.study.fileupload.config;

import com.study.fileupload.jwt.JwtAccessDeniedHandler;
import com.study.fileupload.jwt.JwtAuthenticationEntryPoint;
import com.study.fileupload.jwt.JwtFilter;
import com.study.fileupload.jwt.JwtProvider;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String SECRET_KEY = "replace_this_with_a_very_long_and_secure_secret_key!!";

    @Bean
    public SecretKey createKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(createKey());
    }

    @Bean
    public JwtFilter jwtFilter(
            JwtProvider jwtProvider) {
        return new JwtFilter(jwtProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/logout",
                                "/api/v1/user/signin").permitAll()

                .anyRequest().authenticated())
                .addFilterBefore(jwtFilter(jwtProvider()),UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        (e) ->
                                e.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://172.17.30.27:3000","http://127.0.0.1:3000")); // 프론트엔드 주소
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.setAllowCredentials(true); // 인증정보(쿠키/헤더 등) 허용
        // 파일 업로드 등 content-type 허용
        config.addExposedHeader("Content-Disposition"); // 파일 다운로드 시 필요

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
