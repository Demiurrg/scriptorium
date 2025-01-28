package com.dolgikh.scriptorium.services;

import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.GenreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenresService {
    private final GenresRepository genresRepository;

    public Page<Genre> findAll(Pageable pageable) {
        return genresRepository.findAll(pageable);
    }

    public Page<Book> findBooksByGenre(long id, Pageable pageable) {
        return genresRepository.findBooksByGenreId(id, pageable);
    }

    public Genre findOne(long id) {
        Optional<Genre> genre = genresRepository.findById(id);

        if (genre.isEmpty())
            throw new GenreNotFoundException(id);

        return genre.get();
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
