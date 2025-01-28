package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public Page<Author> findAll(Pageable pageable) {
        return authorsRepository.findAll(pageable);
    }

    public Author findOne(long id) {
        Optional<Author> author = authorsRepository.findById(id);

        if (author.isEmpty())
            throw new AuthorNotFoundException(id);

        return author.get();
    }

    public Page<Book> findBooksByAuthor(long id, Pageable pageable) {
        return authorsRepository.findBooksByAuthorId(id, pageable);
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
