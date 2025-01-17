package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.user.UserAccountResponseDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.UserAccountDetails;
import com.dolgikh.scriptorium.services.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, ModelMapper modelMapper) {
        this.userAccountService = userAccountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public UserAccountResponseDTO showCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccountDetails userAccountDetails = (UserAccountDetails) authentication.getPrincipal();

        UserAccount userAccount = userAccountService.show(userAccountDetails.getUsername());

        return modelMapper.map(userAccount, UserAccountResponseDTO.class);
    }
}
