package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.repositories.BooksRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    public void checkBookExistence(long id) {
        if (!booksRepository.existsById(id))
            throw new BookNotFoundException(id);
    }

    public Page<Book> findAll(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public Book findOne(long id) {
        return booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, long id) {
        checkBookExistence(id);

        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(long id) {
        checkBookExistence(id);

        booksRepository.deleteById(id);
    }
}
