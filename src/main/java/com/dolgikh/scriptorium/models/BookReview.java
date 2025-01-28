package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="book_review")
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

    public BookReview() {}

    public BookReview(int id, UserAccount user, Book book, int rating, String text, Date date) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
