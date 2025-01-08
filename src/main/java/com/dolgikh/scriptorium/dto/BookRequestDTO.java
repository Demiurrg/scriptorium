package com.dolgikh.scriptorium.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

public class BookRequestDTO {
    @Size(min = 2, max = 200)
    private String title;
    private List<Integer> authorIds;
    private List<Integer> genreIds;

    BookRequestDTO() {}

    public BookRequestDTO(String title, List<Integer> authorIds, List<Integer> genreIds) {
        this.title = title;
        this.authorIds = authorIds;
        this.genreIds = genreIds;
    }

    public @Size(min = 2, max = 200) String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 2, max = 200) String title) {
        this.title = title;
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}
