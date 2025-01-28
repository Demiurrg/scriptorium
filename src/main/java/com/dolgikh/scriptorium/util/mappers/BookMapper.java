package com.dolgikh.scriptorium.util.mappers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.AuthorNotFoundException;
import com.dolgikh.scriptorium.util.exceptions.notfoundexceptions.GenreNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AuthorsRepository.class, GenresRepository.class})
public abstract class BookMapper {
    @Autowired
    protected AuthorsRepository authorsRepository;
    @Autowired
    protected GenresRepository genresRepository;

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    public abstract Book toEntity(BookRequestDTO bookRequestDTO);

    @Mapping(source = "authors", target = "authors")
    @Mapping(source = "genres", target = "genres")
    public abstract BookResponseDTO toDTO(Book book);

    public List<BookResponseDTO> toDTOList(List<Book> books) {
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @AfterMapping
    protected void mapAuthorsAndGenres(BookRequestDTO dto, @MappingTarget Book book) {
        List<Author> authors = dto.authorIds().stream()
                .map(authorId -> authorsRepository.findById(authorId)
                        .orElseThrow(() -> new AuthorNotFoundException(authorId)))
                .collect(Collectors.toList());
        book.setAuthors(authors);

        List<Genre> genres = dto.genreIds().stream()
                .map(genreId -> genresRepository.findById(genreId)
                        .orElseThrow(() -> new GenreNotFoundException(genreId)))
                .collect(Collectors.toList());
        book.setGenres(genres);
    }
}