package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.repositories.BooksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    private String notFoundMessage(int id) {
        return "Book with id " + id + " was not found";
    }

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);

        if (book.isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        return book.get();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        if (booksRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        if (booksRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        booksRepository.deleteById(id);
    }
}
