package com.dolgikh.scriptorium.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dolgikh.scriptorium.security.JWTUtil;
import com.dolgikh.scriptorium.services.UserAccountDetailsService;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserAccountDetailsService userAccountDetailsService;
    private final ObjectMapper objectMapper;
    private final List<String> URLs;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserAccountDetailsService userAccountDetailsService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userAccountDetailsService = userAccountDetailsService;
        this.objectMapper = objectMapper;
        this.URLs = new ArrayList<>();
    }

    public void setURLs(String ... URLs) {
        Collections.addAll(this.URLs, URLs);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        for (String exceptionURL : URLs)
            if (path.startsWith(exceptionURL))
                return false;

        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    "JWT Token was not found");
            return;
        }

        String jwt = authHeader.substring(7);

        if (jwt.isBlank()) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Blank JWT Token");
            return;
        }

        try {
            String username = jwtUtil.validateToken(jwt);
            UserDetails userDetails = userAccountDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JWTVerificationException exc) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid JWT Token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(new ErrorResponse(message));

        response.getWriter().write(json);
    }
}
