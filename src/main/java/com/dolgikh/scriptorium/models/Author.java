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
}
