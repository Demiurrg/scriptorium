package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Integer> {
    Genre findByName(String name);
}
