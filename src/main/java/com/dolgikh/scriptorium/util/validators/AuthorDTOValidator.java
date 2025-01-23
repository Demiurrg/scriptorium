package com.dolgikh.scriptorium.util.validators;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.repositories.AuthorsRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;


@Component
public class AuthorDTOValidator implements Validator {
    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorDTOValidator(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return AuthorDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        AuthorDTO authorDTO = (AuthorDTO) target;

        if (authorsRepository.findByName(authorDTO.getName()).isPresent())
            errors.rejectValue("name", "", "Author with name " + authorDTO.getName() + " already exists");

        if (authorDTO.getDateOfBirth().getTime() > new Date().getTime())
            errors.rejectValue("dateOfBirth", "", "Date of birth is in the future");

        if (!authorDTO.isAlive() && authorDTO.getDateOfDeath().getTime() > new Date().getTime())
            errors.rejectValue("dateOfDeath", "", "Date of death is in the future");

        if (!authorDTO.isAlive() && authorDTO.getDateOfDeath().getTime() < authorDTO.getDateOfBirth().getTime())
            errors.rejectValue("dateOfDeath", "", "Date of death is later than date of birth");
    }
}
