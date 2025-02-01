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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public void checkAuthorExistence(long id) {
        if (authorsRepository.existsById(id))
            throw new AuthorNotFoundException(id);
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorsRepository.findAll(pageable);
    }

    public Author findOne(long id) {
        return authorsRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
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
        checkAuthorExistence(id);

        author.setId(id);
        authorsRepository.save(author);
    }

    @Transactional
    public void delete(long id) {
        checkAuthorExistence(id);

        authorsRepository.deleteById(id);
    }
}
