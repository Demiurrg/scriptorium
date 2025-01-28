package com.dolgikh.scriptorium.dto.books;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;

import java.util.List;

public record BookResponseDTO(String title, List<AuthorDTO> authors, List<GenreDTO> genres) {
}
