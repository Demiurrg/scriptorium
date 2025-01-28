package com.dolgikh.scriptorium.dto;

import jakarta.validation.constraints.Size;

public record GenreDTO(@Size(min = 2, max = 100) String name) {
}
