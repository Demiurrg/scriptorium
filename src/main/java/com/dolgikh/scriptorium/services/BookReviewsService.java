package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.BookReview;
import com.dolgikh.scriptorium.repositories.BookReviewsRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReviewsService {
    private final BookReviewsRepository bookReviewsRepository;

    public void checkReviewExistence(long id) {
        if (bookReviewsRepository.existsById(id))
            throw new ReviewNotFoundException(id);
    }

    public List<BookReview> findReviewsForBook(long bookId) {
        return bookReviewsRepository.findByBookId(bookId);
    }

    public List<BookReview> findReviewsForUser(long userId) {
        return bookReviewsRepository.findByUserId(userId);
    }

    @Transactional
    public void save(BookReview bookReview) {
        bookReviewsRepository.save(bookReview);
    }

    @Transactional
    public void update(BookReview bookReview, long id) {
        checkReviewExistence(id);

        bookReview.setId(id);
        bookReviewsRepository.save(bookReview);
    }

    @Transactional
    public void delete(long id) {
        checkReviewExistence(id);

        bookReviewsRepository.deleteById(id);
    }
}
