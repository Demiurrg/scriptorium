package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    private String notFoundMessage(int id) {
        return "Author with id " + id + " was not found";
    }

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findOne(int id) {
        Optional<Author> author = authorsRepository.findById(id);

        if (author.isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        return author.get();
    }

    @Transactional
    public void save(Author author) {
        authorsRepository.save(author);
    }

    @Transactional
    public void update(Author author, int id) {
        if (authorsRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        author.setId(id);
        authorsRepository.save(author);
    }

    @Transactional
    public void delete(int id) {
        if (authorsRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        authorsRepository.deleteById(id);
    }
}
