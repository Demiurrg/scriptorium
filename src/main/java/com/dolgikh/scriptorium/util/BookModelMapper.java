package com.dolgikh.scriptorium.util;

import com.dolgikh.scriptorium.dto.BookRequestDTO;
import com.dolgikh.scriptorium.dto.BookResponseDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookModelMapper {
    private final AuthorsRepository authorsRepository;
    private final GenresRepository genresRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookModelMapper(AuthorsRepository authorsRepository, GenresRepository genresRepository, ModelMapper modelMapper) {
        this.authorsRepository = authorsRepository;
        this.genresRepository = genresRepository;
        this.modelMapper = modelMapper;
    }

    public Book DTOtoBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());

        for (int authorId : bookRequestDTO.getAuthorIds()) {
            Author author = authorsRepository.findById(authorId).orElse(null);
            book.addAuthor(author);
        }

        for (int genreId : bookRequestDTO.getGenreIds()) {
            Genre genre = genresRepository.findById(genreId).orElse(null);
            book.addGenre(genre);
        }
        return book;
    }

    public BookResponseDTO bookToDTO(Book book) {
        return modelMapper.map(book, BookResponseDTO.class);
    }
}
