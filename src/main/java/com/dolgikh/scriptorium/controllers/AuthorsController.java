package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.AuthorsService;
import com.dolgikh.scriptorium.util.mappers.AuthorMapper;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import com.dolgikh.scriptorium.util.validators.AuthorDTOValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Page<AuthorDTO> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return authorsService.findAll(PageRequest.of(page, size)).map(authorMapper::toDTO);
    }

    @GetMapping("/{id}")
    public AuthorDTO show(@PathVariable long id) {
        return authorMapper.toDTO(authorsService.findOne(id));
    }

    @GetMapping("/{id}/books")
    public Page<BookResponseDTO> getBooksByAuthor(@PathVariable long id,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return authorsService.findBooksByAuthor(id, PageRequest.of(page, size)).map(bookMapper::toDTO);
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
