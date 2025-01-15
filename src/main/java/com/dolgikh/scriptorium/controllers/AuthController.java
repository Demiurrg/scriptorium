package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.UserAccountDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.JWTUtil;
import com.dolgikh.scriptorium.services.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AuthController(ModelMapper modelMapper, UserAccountService userAccountService, JWTUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody UserAccountDTO userAccountDTO) {
        UserAccount userAccount = modelMapper.map(userAccountDTO, UserAccount.class);
        userAccountService.save(userAccount);
        return Map.of("jwt-token", jwtUtil.generateToken(userAccount.getUsername()));
    }
}
