package com.dolgikh.scriptorium.dto.users;

import com.dolgikh.scriptorium.dto.books.ReadBookDTO;

import java.util.List;

public record UserAccountResponseDTO(String username, List<ReadBookDTO> readBooks) {
}
