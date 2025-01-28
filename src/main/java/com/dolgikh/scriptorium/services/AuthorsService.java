package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findOne(long id) {
        Optional<Author> author = authorsRepository.findById(id);

        if (author.isEmpty())
            throw new AuthorNotFoundException(id);

        return author.get();
    }

    public List<Book> findBooksOfAuthor(long id) {
        return findOne(id).getBooks();
    }

    @Transactional
    public void save(Author author) {
        if (authorsRepository.findByName(author.getName()).isPresent())
            throw new IllegalArgumentException("Author with name " + author.getName() + " already exists");

        authorsRepository.save(author);
    }

    @Transactional
    public void update(Author author, long id) {
        if (authorsRepository.findById(id).isEmpty())
            throw new AuthorNotFoundException(id);

        author.setId(id);
        authorsRepository.save(author);
    }

    @Transactional
    public void delete(long id) {
        if (authorsRepository.findById(id).isEmpty())
            throw new AuthorNotFoundException(id);

        authorsRepository.deleteById(id);
    }
}
