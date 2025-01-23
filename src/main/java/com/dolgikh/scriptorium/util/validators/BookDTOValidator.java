package com.dolgikh.scriptorium.util.validators;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class BookDTOValidator implements Validator {
    private final AuthorsRepository authorsRepository;
    private final GenresRepository genresRepository;

    @Autowired
    public BookDTOValidator(AuthorsRepository authorsRepository, GenresRepository genresRepository) {
        this.authorsRepository = authorsRepository;
        this.genresRepository = genresRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return BookRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        BookRequestDTO book = (BookRequestDTO) target;
        List<Integer> authorIds = book.getAuthorIds();
        List<Integer> genreIds = book.getGenreIds();

        for (int authorId : authorIds) {
            if (authorsRepository.findById(authorId).isEmpty())
                errors.rejectValue("authorIds", "", "Author with ID " + authorId + " does not exist");
        }

        for (int genreId : genreIds) {
            if (genresRepository.findById(genreId).isEmpty())
                errors.rejectValue("genreIds", "", "Genre with ID " + genreId + " does not exist");
        }
    }
}
