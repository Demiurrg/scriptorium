package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GenresService {
    private final GenresRepository genresRepository;

    private String notFoundMessage(int id) {
        return "Genre with id " + id + " was not found";
    }

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    public Genre findOne(int id) {
        Optional<Genre> genre = genresRepository.findById(id);

        if (genre.isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        return genre.get();
    }

    public List<Book> findBooksOfGenre(int id) {
        return findOne(id).getBooks();
    }

    @Transactional
    public void save(Genre genre) {
        genresRepository.save(genre);
    }

    @Transactional
    public void update(Genre genre, int id) {
        if (genresRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        genre.setId(id);
        genresRepository.save(genre);
    }

    @Transactional
    public void delete(int id) {
        if (genresRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(notFoundMessage(id));

        genresRepository.deleteById(id);
    }
}
