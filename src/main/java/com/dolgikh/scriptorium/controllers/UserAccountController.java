package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookIdDTO;
import com.dolgikh.scriptorium.dto.users.UserAccountResponseDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.UserAccountDetails;
import com.dolgikh.scriptorium.services.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final ModelMapper modelMapper;

    private UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccountDetails userAccountDetails = (UserAccountDetails) authentication.getPrincipal();
        return userAccountService.findByUsername(userAccountDetails.getUsername());
    }

    @Autowired
    public UserAccountController(UserAccountService userAccountService, ModelMapper modelMapper) {
        this.userAccountService = userAccountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public UserAccountResponseDTO showCurrentUser() {
        return modelMapper.map(getCurrentUser(), UserAccountResponseDTO.class);
    }

    @PostMapping("/readBooks")
    public void addBookToRead(@RequestBody BookIdDTO bookIdDTO) {
        userAccountService.addBookToRead(getCurrentUser(), bookIdDTO.getId());
    }

    @DeleteMapping("/readBooks")
    public void deleteBookFromRead(@RequestBody BookIdDTO bookIdDTO) {
        userAccountService.deleteBookFromRead(getCurrentUser().getId(), bookIdDTO.getId());
    }
}
