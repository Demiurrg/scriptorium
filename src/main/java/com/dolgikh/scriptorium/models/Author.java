package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="author")
public class Author {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name="date_of_death")
    private Date dateOfDeath;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author() {}

    public Author(String name, Date dateOfBirth, Date dateOfDeath) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
