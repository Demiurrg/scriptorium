package com.dolgikh.scriptorium.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dolgikh.scriptorium.security.JWTUtil;
import com.dolgikh.scriptorium.services.UserAccountDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserAccountDetailsService userAccountDetailsService;
    private final ObjectMapper objectMapper;
    private final List<String> URLs;

    public void setURLs(String ... URLs) {
        Collections.addAll(this.URLs, URLs);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        String path = request.getServletPath();

        for (String exceptionURL : URLs)
            if (path.startsWith(exceptionURL))
                return false;

        return true;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            setErrorResponse(response, "JWT Token was not found");
            return;
        }

        String jwt = authHeader.substring(7);

        if (jwt.isBlank()) {
            setErrorResponse(response, "Blank JWT Token");
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
            setErrorResponse(response, "Invalid JWT Token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(Map.of("message", message));

        response.getWriter().write(json);
    }
}
