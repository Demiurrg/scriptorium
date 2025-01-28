package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="book_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReview {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "text")
    private String text;

    @Column(name = "date", nullable = false)
    private Date date;
}
