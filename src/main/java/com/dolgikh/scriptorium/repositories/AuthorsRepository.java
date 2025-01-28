package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    Page<Book> findBooksByAuthorId(@Param("authorId") long authorId, Pageable pageable);
}
