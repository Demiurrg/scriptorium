package com.dolgikh.scriptorium.util.validators;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class AuthorDTOValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return AuthorDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        AuthorDTO authorDTO = (AuthorDTO) target;
        LocalDateTime now = LocalDateTime.now();

        if (authorDTO.dateOfBirth().isAfter(now))
            errors.rejectValue("dateOfBirth", "", "Date of birth is in the future");

        if (!authorDTO.isAlive() && authorDTO.dateOfDeath().isAfter(now))
            errors.rejectValue("dateOfDeath", "", "Date of death is in the future");

        if (!authorDTO.isAlive() && authorDTO.dateOfDeath().isBefore(authorDTO.dateOfBirth()))
            errors.rejectValue("dateOfDeath", "", "Date of death is later than date of birth");
    }
}
