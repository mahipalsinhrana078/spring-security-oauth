package com.baeldung.resource.config;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Component
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        JwtAuthenticationToken principal = (JwtAuthenticationToken) ((HttpServletRequest) request).getUserPrincipal();
        String username = principal.getToken().getClaimAsString("preferred_username");
        if (principal != null) {
            if (username.endsWith("@test.com")) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Domain is not validate");
            }
        }

    }
}

