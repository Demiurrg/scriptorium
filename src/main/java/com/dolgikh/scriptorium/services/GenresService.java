package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GenresService {
    private final GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    public Genre findOne(int id) {
        return genresRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Genre genre) {
        genresRepository.save(genre);
    }

    @Transactional
    public void delete(int id) {
        genresRepository.deleteById(id);
    }
}
