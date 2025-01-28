package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.AuthorsService;
import com.dolgikh.scriptorium.util.mappers.AuthorMapper;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import com.dolgikh.scriptorium.util.validators.AuthorDTOValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorsController {
    private final AuthorsService authorsService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final AuthorDTOValidator authorDTOValidator;

    private String printFieldErrors(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : fieldErrors)
            errorMessage
                    .append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");

        return errorMessage.toString();
    }

    @GetMapping
    public List<AuthorDTO> index() {
        return authorsService.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDTO show(@PathVariable long id) {
        return authorMapper.toDTO(authorsService.findOne(id));
    }

    @GetMapping("/{id}/books")
    public List<BookResponseDTO> getBooksOfAuthor(@PathVariable long id) {
        return bookMapper.toDTOList(authorsService.findBooksOfAuthor(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult) {
        authorDTOValidator.validate(authorDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(printFieldErrors(bindingResult.getFieldErrors()));

        authorsService.save(authorMapper.toEntity(authorDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult, @PathVariable long id) {
        authorDTOValidator.validate(authorDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(printFieldErrors(bindingResult.getFieldErrors()));

        authorsService.update(authorMapper.toEntity(authorDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        authorsService.delete(id);
    }
}
