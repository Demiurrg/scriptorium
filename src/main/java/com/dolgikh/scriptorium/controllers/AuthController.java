package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.user.UserAccountRequestDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.JWTUtil;
import com.dolgikh.scriptorium.services.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ModelMapper modelMapper;
    private final UserAccountService userAccountService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(ModelMapper modelMapper, UserAccountService userAccountService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.modelMapper = modelMapper;
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody UserAccountRequestDTO userAccountRequestDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        userAccountRequestDTO.getUsername(),
                        userAccountRequestDTO.getPassword()
                );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        return Map.of("jwt-token", jwtUtil.generateToken(userAccountRequestDTO.getUsername()));
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody UserAccountRequestDTO userAccountRequestDTO) {
        UserAccount userAccount = modelMapper.map(userAccountRequestDTO, UserAccount.class);
        userAccountService.save(userAccount);
        return Map.of("jwt-token", jwtUtil.generateToken(userAccount.getUsername()));
    }
}
