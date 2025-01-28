package com.dolgikh.scriptorium.dto.books;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReadBookDTO {
    private long bookId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    public long getBookId() {
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
