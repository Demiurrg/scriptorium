package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenresRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.id = :genreId")
    Page<Book> findBooksByGenreId(@Param("genreId") long genreId, Pageable pageable);
}
