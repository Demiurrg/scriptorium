package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.JWTUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserAccountService userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthService(UserAccountService userAccountService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userAccountService = userAccountService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        if (userAccountService.findByUsername(username) == null)
            throw new EntityNotFoundException("User not found");

        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        }

        return jwtUtil.generateToken(username);
    }

    public String registration(String username, String password) {
        if (userAccountService.findByUsername(username) != null)
            throw new IllegalArgumentException("User with this name already exists");

        userAccountService.save(new UserAccount(username, password));
        return jwtUtil.generateToken(username);
    }
}
