package com.dolgikh.scriptorium.dto;

import java.util.List;

public class BookDTO {
    private String title;
    private List<AuthorDTO> authors;
    private List<GenreDTO> genres;

    BookDTO() {}

    public BookDTO(String title, List<AuthorDTO> authors, List<GenreDTO> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
