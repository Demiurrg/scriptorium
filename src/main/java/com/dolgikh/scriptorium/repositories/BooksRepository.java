package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
