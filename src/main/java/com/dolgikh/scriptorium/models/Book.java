package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="title", nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name="author_book",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name="book_genre",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "book")
    private List<UserReadingHistory> readingHistoryList;

//    public Book() {}
//
//    public Book(long id, String title, List<Author> authors, List<Genre> genres, List<UserReadingHistory> readingHistoryList) {
//        this.id = id;
//        this.title = title;
//        this.authors = authors;
//        this.genres = genres;
//        this.readingHistoryList = readingHistoryList;
//    }
//
//    public void addAuthor(Author author) {
//        if (authors == null)
//            authors = new ArrayList<>();
//        authors.add(author);
//    }
//
//    public void addGenre(Genre genre) {
//        if (genres == null)
//            genres = new ArrayList<>();
//        genres.add(genre);
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public List<Author> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(List<Author> authors) {
//        this.authors = authors;
//    }
//
//    public List<Genre> getGenres() {
//        return genres;
//    }
//
//    public void setGenres(List<Genre> genres) {
//        this.genres = genres;
//    }
//
//    public List<UserReadingHistory> getReadingHistoryList() {
//        return readingHistoryList;
//    }
//
//    public void setReadingHistoryList(List<UserReadingHistory> readingHistoryList) {
//        this.readingHistoryList = readingHistoryList;
//    }
}
