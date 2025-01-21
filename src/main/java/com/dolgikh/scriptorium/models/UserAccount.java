package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_account")
public class UserAccount {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserReadingHistory> readingHistoryList;

    public UserAccount() {}

    public UserAccount(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserReadingHistory> getReadingHistoryList() {
        return readingHistoryList;
    }

    public void setReadingHistoryList(List<UserReadingHistory> readingHistoryList) {
        this.readingHistoryList = readingHistoryList;
    }

    public void readBook(Book book) {
        if (readingHistoryList == null)
            readingHistoryList = new ArrayList<>();

        readingHistoryList.add(new UserReadingHistory(this, book, new Date()));
    }
}
