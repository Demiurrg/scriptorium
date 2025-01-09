package com.dolgikh.scriptorium.dto;

import jakarta.validation.constraints.Size;

public class GenreDTO {
    @Size(min = 2, max = 100)
    private String name;

    public GenreDTO() {}

    public GenreDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
