package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.users.UserAccountRequestDTO;
import com.dolgikh.scriptorium.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String performLogin(@RequestBody @Valid UserAccountRequestDTO userAccountRequestDTO) {
        return authService.login(userAccountRequestDTO.username(), userAccountRequestDTO.password());
    }

    @PostMapping("/registration")
    public String performRegistration(@RequestBody @Valid UserAccountRequestDTO userAccountRequestDTO) {
        return authService.registration(userAccountRequestDTO.username(), userAccountRequestDTO.password());
    }
}
