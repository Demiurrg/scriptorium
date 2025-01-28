package com.dolgikh.scriptorium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record AuthorDTO(
        @Size(min = 2, max = 200) String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") Date dateOfBirth,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") Date dateOfDeath) {

    public boolean isAlive() {
        return dateOfDeath == null;
    }
}
