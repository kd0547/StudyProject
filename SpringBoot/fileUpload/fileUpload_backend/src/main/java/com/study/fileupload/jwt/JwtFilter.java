package com.study.fileupload.jwt;

import ch.qos.logback.core.util.StringUtil;
import com.study.fileupload.exception.ErrorBlock;
import com.study.fileupload.exception.ErrorCode;
import com.study.fileupload.exception.JwtNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        JwtFilterExceptionHandling(request,response,filterChain,()-> {
            String authorization = getJwtFromRequest(request);
            if(jwtProvider.isExpired(authorization)) {
                String username = jwtProvider.getUsername(authorization);
                List<GrantedAuthority> authorities = jwtProvider.getAuthorities(authorization);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        });
    }

    private String getJwtFromRequest(HttpServletRequest request) throws AuthenticationException  {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new JwtNotFoundException("jwt token is not found");
    }

    private void JwtFilterExceptionHandling(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain filterChain, Runnable jwtLogic)
            throws ServletException, IOException {
        try {
            jwtLogic.run();
        } catch (JwtNotFoundException jwtNotFoundException) {
            ErrorBlock errorBlock = new ErrorBlock(ErrorCode.JwtNotFound,jwtNotFoundException.getMessage());
            request.setAttribute("exception",errorBlock);
        } catch (MalformedJwtException malformedJwtException) {
            ErrorBlock errorBlock = new ErrorBlock(ErrorCode.InvalidJwt,malformedJwtException.getMessage());
            request.setAttribute("exception",errorBlock);
        } catch (ExpiredJwtException expiredJwtException) {
            ErrorBlock errorBlock = new ErrorBlock(ErrorCode.ExpiredJwt,expiredJwtException.getMessage());
            request.setAttribute("exception",errorBlock);
        } catch (Exception e) {
            ErrorBlock errorBlock = new ErrorBlock(ErrorCode.ERROR_CODE,e.getMessage());
            request.setAttribute("exception",errorBlock);
        } finally {
            filterChain.doFilter(request,response);
        }

    }
}
