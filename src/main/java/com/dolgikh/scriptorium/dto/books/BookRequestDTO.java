package com.dolgikh.scriptorium.dto.books;

import jakarta.validation.constraints.Size;

import java.util.List;

public class BookRequestDTO {
    @Size(min = 2, max = 200)
    private String title;
    private List<Long> authorIds;
    private List<Long> genreIds;

    BookRequestDTO() {}

    public BookRequestDTO(String title, List<Long> authorIds, List<Long> genreIds) {
        this.title = title;
        this.authorIds = authorIds;
        this.genreIds = genreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }
}
