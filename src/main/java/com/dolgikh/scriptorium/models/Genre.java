package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books;
}
