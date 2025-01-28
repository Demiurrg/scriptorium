package com.dolgikh.scriptorium.dto;

import jakarta.validation.constraints.Size;

public record GenreDTO(@Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters") String name) {
}
