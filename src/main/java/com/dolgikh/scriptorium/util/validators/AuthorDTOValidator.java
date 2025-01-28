package com.dolgikh.scriptorium.util.validators;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;


@Component
public class AuthorDTOValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return AuthorDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        AuthorDTO authorDTO = (AuthorDTO) target;

        if (authorDTO.dateOfBirth().getTime() > new Date().getTime())
            errors.rejectValue("dateOfBirth", "", "Date of birth is in the future");

        if (!authorDTO.isAlive() && authorDTO.dateOfDeath().getTime() > new Date().getTime())
            errors.rejectValue("dateOfDeath", "", "Date of death is in the future");

        if (!authorDTO.isAlive() && authorDTO.dateOfDeath().getTime() < authorDTO.dateOfBirth().getTime())
            errors.rejectValue("dateOfDeath", "", "Date of death is later than date of birth");
    }
}
