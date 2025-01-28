package com.dolgikh.scriptorium.dto.books;

import jakarta.validation.constraints.Size;

import java.util.List;

public record BookRequestDTO(@Size(min = 2, max = 200, message = "Name should be between 2 and 100 characters") String title, List<Long> authorIds, List<Long> genreIds) {
}

