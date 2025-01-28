package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.BookReview;
import com.dolgikh.scriptorium.repositories.BookReviewsRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookReviewsService {
    private final BookReviewsRepository bookReviewsRepository;

    @Autowired
    public BookReviewsService(BookReviewsRepository bookReviewsRepository) {
        this.bookReviewsRepository = bookReviewsRepository;
    }

    public List<BookReview> findReviewsForBook(int bookId) {
        return bookReviewsRepository.findByBookId(bookId);
    }

    public List<BookReview> findReviewsForUser(int userId) {
        return bookReviewsRepository.findByUserId(userId);
    }

    @Transactional
    public void save(BookReview bookReview) {
        bookReviewsRepository.save(bookReview);
    }

    @Transactional
    public void update(BookReview bookReview, int id) {
        if (bookReviewsRepository.findById(id).isEmpty())
            throw new ReviewNotFoundException(id);

        bookReview.setId(id);
        bookReviewsRepository.save(bookReview);
    }

    @Transactional
    public void delete(int id) {
        if (bookReviewsRepository.findById(id).isEmpty())
            throw new ReviewNotFoundException(id);

        bookReviewsRepository.deleteById(id);
    }
}
