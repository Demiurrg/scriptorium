package com.dolgikh.scriptorium.dto.books;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReadBookDTO {
    private int bookId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
