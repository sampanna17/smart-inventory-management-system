package com.smartinventorysystem.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            @NonNull HttpServletRequest request,
            HttpServletResponse response,
            @NonNull AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String authError = (String) request.getAttribute("auth_error");
        String errorMessage = (authError != null) ? authError : "Authentication required or invalid token";

        response.getWriter().write(String.format("""
        {
           "success": false,
            "message": "Unauthorized",
         "errors": ["%s"]
        }
        """, errorMessage));
    }
}