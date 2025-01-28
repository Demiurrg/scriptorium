package com.dolgikh.scriptorium.dto.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserAccountRequestDTO(
        @NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters") String username,
        @NotEmpty(message = "Password should not be empty") @Size(min = 8, max = 100, message = "Password should be between 8 and 100 characters") String password) {
}
