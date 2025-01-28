package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.GenreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Genre findOne(long id) {
        Optional<Genre> genre = genresRepository.findById(id);

        if (genre.isEmpty())
            throw new GenreNotFoundException(id);

        return genre.get();
    }

    public List<Book> findBooksOfGenre(long id) {
        return findOne(id).getBooks();
    }

    @Transactional
    public void save(Genre genre) {
        genresRepository.save(genre);
    }

    @Transactional
    public void update(Genre genre, long id) {
        if (genresRepository.findById(id).isEmpty())
            throw new GenreNotFoundException(id);

        genre.setId(id);
        genresRepository.save(genre);
    }

    @Transactional
    public void delete(long id) {
        if (genresRepository.findById(id).isEmpty())
            throw new GenreNotFoundException(id);

        genresRepository.deleteById(id);
    }
}
