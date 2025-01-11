package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.dto.BookResponseDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.services.AuthorsService;
import com.dolgikh.scriptorium.util.BookModelMapper;
import com.dolgikh.scriptorium.util.ErrorResponse;
import com.dolgikh.scriptorium.util.validators.AuthorDTOValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorsService authorsService;
    private final ModelMapper modelMapper;
    private final BookModelMapper bookModelMapper;
    private final AuthorDTOValidator authorDTOValidator;

    @Autowired
    public AuthorsController(AuthorsService authorsService, ModelMapper modelMapper, BookModelMapper bookModelMapper, AuthorDTOValidator authorDTOValidator) {
        this.authorsService = authorsService;
        this.modelMapper = modelMapper;
        this.bookModelMapper = bookModelMapper;
        this.authorDTOValidator = authorDTOValidator;
    }

    @GetMapping()
    public List<AuthorDTO> index() {
        return authorsService.findAll()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDTO show(@PathVariable Integer id) {
        return modelMapper.map(authorsService.findOne(id), AuthorDTO.class);
    }

    @GetMapping("/{id}/books")
    public List<BookResponseDTO> getBooksOfAuthor(@PathVariable Integer id) {
        return bookModelMapper.allBooksToDTO(authorsService.findBooksOfAuthor(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult) {
        authorDTOValidator.validate(authorDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        authorsService.save(modelMapper.map(authorDTO, Author.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult, @PathVariable Integer id) {
        authorDTOValidator.validate(authorDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        authorsService.update(modelMapper.map(authorDTO, Author.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        authorsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
