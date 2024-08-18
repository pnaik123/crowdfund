package com.intuit.crowdfunds.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.intuit.crowdfunds.config.UserAuthenticationProvider;
import com.intuit.crowdfunds.constants.Constants;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2
                    && Constants.Auth.BEARER.equals(authElements[0])) {
                try {
                    if (HttpMethod.GET.name().equals(request.getMethod())) {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthenticationProvider.validateToken(authElements[1]));
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthenticationProvider.validateTokenStrongly(authElements[1]));
                    }
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
