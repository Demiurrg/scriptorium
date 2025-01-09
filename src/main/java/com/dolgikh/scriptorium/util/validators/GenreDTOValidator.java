package com.dolgikh.scriptorium.util.validators;

import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GenreDTOValidator implements Validator {
    private final GenresRepository genresRepository;

    @Autowired
    public GenreDTOValidator(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return GenreDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GenreDTO genreDTO = (GenreDTO) target;
        if (genresRepository.findByName(genreDTO.getName()) != null)
            errors.rejectValue("name", "", "Genre " + genreDTO.getName() + " already exists");
    }
}