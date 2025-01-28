package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="user_account")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name="username", nullable = false)
    private String username;

    @NonNull
    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserReadingHistory> readBooks;
}
