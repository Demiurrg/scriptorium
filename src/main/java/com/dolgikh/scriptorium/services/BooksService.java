package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.repositories.BooksRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(long id) {
        Optional<Book> book = booksRepository.findById(id);

        if (book.isEmpty())
            throw new BookNotFoundException(id);

        return book.get();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, long id) {
        if (booksRepository.findById(id).isEmpty())
            throw new BookNotFoundException(id);

        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(long id) {
        if (booksRepository.findById(id).isEmpty())
            throw new BookNotFoundException(id);

        booksRepository.deleteById(id);
    }
}
