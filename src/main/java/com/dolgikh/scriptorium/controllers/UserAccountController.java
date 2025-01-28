package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookIdDTO;
import com.dolgikh.scriptorium.dto.users.UserAccountResponseDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import com.dolgikh.scriptorium.security.UserAccountDetails;
import com.dolgikh.scriptorium.services.UserAccountService;
import com.dolgikh.scriptorium.util.mappers.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    private UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccountDetails userAccountDetails = (UserAccountDetails) authentication.getPrincipal();
        return userAccountService.findByUsername(userAccountDetails.getUsername());
    }

    @GetMapping
    public UserAccountResponseDTO showCurrentUser() {
        return userAccountMapper.toDTO(getCurrentUser());
    }

    @PostMapping("/readBooks")
    public void addBookToRead(@RequestBody BookIdDTO bookIdDTO) {
        userAccountService.addBookToRead(getCurrentUser(), bookIdDTO.id());
    }

    @DeleteMapping("/readBooks")
    public void deleteBookFromRead(@RequestBody BookIdDTO bookIdDTO) {
        userAccountService.deleteBookFromRead(getCurrentUser().getId(), bookIdDTO.id());
    }
}
