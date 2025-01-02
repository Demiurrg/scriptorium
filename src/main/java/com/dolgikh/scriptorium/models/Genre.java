package com.dolgikh.scriptorium.models;

import jakarta.persistence.*;

@Entity
@Table(name="genre")
public class Genre {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
}
