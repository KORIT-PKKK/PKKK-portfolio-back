package com.portfolio.springapplication.security.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/api/auth/signout",
            "/api/post/add", "/api/post/update", "/api/post/delete",
            "/api/user/detail/update", "api/user/password/change","/api/user/subscribe", "/api/user/favorite",
            "/api/user/post"
            );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (excludeFilter(httpRequest)) {

            String accessToken = httpRequest.getHeader("Authorization");
            accessToken = jwtTokenProvider.getToken(accessToken);
            boolean isValid = jwtTokenProvider.validateToken(accessToken);

            if (isValid) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean excludeFilter(HttpServletRequest httpRequest) {
        return EXCLUDE_URLS.stream().anyMatch(url -> httpRequest.getRequestURI().contains(url));
    }
}
