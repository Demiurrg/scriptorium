package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findOne(int id) {
        return authorsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Author author) {
        authorsRepository.save(author);
    }

    @Transactional
    public void delete(int id) {
        authorsRepository.deleteById(id);
    }
}
