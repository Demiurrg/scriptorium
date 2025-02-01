package com.dolgikh.scriptorium.dto.books;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReadBookDTO(long bookId, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") LocalDateTime date) {
}
