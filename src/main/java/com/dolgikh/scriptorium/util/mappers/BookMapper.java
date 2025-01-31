package com.dolgikh.scriptorium.util.mappers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.services.AuthorsService;
import com.dolgikh.scriptorium.services.GenresService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AuthorsService.class, GenresService.class})
public abstract class BookMapper {
    @Autowired
    protected AuthorsService authorsService;
    @Autowired
    protected GenresService genresService;

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    public abstract Book toEntity(BookRequestDTO bookRequestDTO);

    @Mapping(source = "authors", target = "authors")
    @Mapping(source = "genres", target = "genres")
    public abstract BookResponseDTO toDTO(Book book);

    @AfterMapping
    protected void mapAuthorsAndGenres(BookRequestDTO dto, @MappingTarget Book book) {
        List<Author> authors = dto.authorIds().stream()
                .map(authorId -> authorsService.findOne(authorId))
                .collect(Collectors.toList());

        book.setAuthors(authors);

        List<Genre> genres = dto.genreIds().stream()
                .map(genreId -> genresService.findOne(genreId))
                .collect(Collectors.toList());

        book.setGenres(genres);
    }
}