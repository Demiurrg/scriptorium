package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenresRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
