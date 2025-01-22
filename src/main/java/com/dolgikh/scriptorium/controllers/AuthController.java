package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.users.UserAccountRequestDTO;
import com.dolgikh.scriptorium.services.AuthService;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> performLogin(@RequestBody @Valid UserAccountRequestDTO userAccountRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        return ResponseEntity.ok(authService.login(userAccountRequestDTO.getUsername(), userAccountRequestDTO.getPassword()));
    }

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid UserAccountRequestDTO userAccountRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        return ResponseEntity.ok(authService.registration(userAccountRequestDTO.getUsername(), userAccountRequestDTO.getPassword()));
    }
}
