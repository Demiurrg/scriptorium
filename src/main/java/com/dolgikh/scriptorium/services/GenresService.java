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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenresService {
    private final GenresRepository genresRepository;

    public void checkGenreExistence(long id) {
        if(!genresRepository.existsById(id))
            throw new GenreNotFoundException(id);
    }

    public Page<Genre> findAll(Pageable pageable) {
        return genresRepository.findAll(pageable);
    }

    public Page<Book> findBooksByGenre(long id, Pageable pageable) {
        return genresRepository.findBooksByGenreId(id, pageable);
    }

    public Genre findOne(long id) {
        return genresRepository.findById(id).orElseThrow(() -> new GenreNotFoundException(id));
    }

    @Transactional
    public void save(Genre genre) {
        genresRepository.save(genre);
    }

    @Transactional
    public void update(Genre genre, long id) {
        checkGenreExistence(id);

        genre.setId(id);
        genresRepository.save(genre);
    }

    @Transactional
    public void delete(long id) {
        checkGenreExistence(id);

        genresRepository.deleteById(id);
    }
}
