package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_reading_history")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserReadingHistory {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @NonNull
    @Column(name = "date", nullable = false)
    private Date date;
}
