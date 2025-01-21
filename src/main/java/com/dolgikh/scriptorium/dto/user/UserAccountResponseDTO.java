package com.dolgikh.scriptorium.dto.user;

import java.util.List;

public class UserAccountResponseDTO {
    private String username;
    private List<ReadBookDTO> readBooks;

    UserAccountResponseDTO() {}

    public UserAccountResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ReadBookDTO> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<ReadBookDTO> readBooks) {
        this.readBooks = readBooks;
    }
}
