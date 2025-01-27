package com.dolgikh.scriptorium.repositories;

import com.dolgikh.scriptorium.models.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewsRepository extends JpaRepository<BookReview, Integer> {
    List<BookReview> findByBookId(int bookId);

    List<BookReview> findByUserId(int userId);
}
