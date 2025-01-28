package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.JWTUtil;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountService userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public String login(String username, String password) {
        if (!userAccountService.doesUserExist(username))
            throw new UserNotFoundException(username);

        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        }

        return jwtUtil.generateToken(username);
    }

    public String registration(String username, String password) {
        if (userAccountService.doesUserExist(username))
            throw new IllegalArgumentException("User with this name already exists");

        userAccountService.save(new UserAccount(username, password));
        return jwtUtil.generateToken(username);
    }
}
