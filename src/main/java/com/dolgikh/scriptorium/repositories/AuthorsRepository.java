package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}
