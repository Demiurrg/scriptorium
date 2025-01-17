package com.dolgikh.scriptorium.dto.user;

public class UserAccountResponseDTO {
    private String username;

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
}
