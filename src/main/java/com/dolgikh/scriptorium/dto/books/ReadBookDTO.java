package com.dolgikh.scriptorium.dto.books;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record ReadBookDTO(long bookId, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") Date date) {
}
