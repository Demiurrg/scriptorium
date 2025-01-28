package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewsRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findByBookId(long bookId);

    List<BookReview> findByUserId(long userId);
}
