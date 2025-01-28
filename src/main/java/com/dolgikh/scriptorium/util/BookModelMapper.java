package com.dolgikh.scriptorium.util;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookModelMapper {
    private final AuthorsRepository authorsRepository;
    private final GenresRepository genresRepository;
    private final ModelMapper modelMapper;

    public Book DTOtoBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());

        for (long authorId : bookRequestDTO.getAuthorIds()) {
            Author author = authorsRepository.findById(authorId).orElse(null);
            book.addAuthor(author);
        }

        for (long genreId : bookRequestDTO.getGenreIds()) {
            Genre genre = genresRepository.findById(genreId).orElse(null);
            book.addGenre(genre);
        }
        return book;
    }

    public BookResponseDTO bookToDTO(Book book) {
        return modelMapper.map(book, BookResponseDTO.class);
    }

    public List<BookResponseDTO> allBooksToDTO(List<Book> books) {
        return books.stream()
                .map(this::bookToDTO)
                .collect(Collectors.toList());
    }
}
