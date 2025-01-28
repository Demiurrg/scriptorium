package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name="date_of_death")
    private Date dateOfDeath;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
