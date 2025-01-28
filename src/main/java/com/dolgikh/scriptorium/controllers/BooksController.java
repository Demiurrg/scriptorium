package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.BooksService;
import com.dolgikh.scriptorium.util.validators.BookDTOValidator;
import com.dolgikh.scriptorium.util.BookModelMapper;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final BookModelMapper bookModelMapper;
    private final BookDTOValidator bookDTOValidator;

    @Autowired
    public BooksController(BooksService booksService, BookModelMapper bookModelMapper, BookDTOValidator bookDTOValidator) {
        this.booksService = booksService;
        this.bookModelMapper = bookModelMapper;
        this.bookDTOValidator = bookDTOValidator;
    }

    @GetMapping
    public List<BookResponseDTO> index() {
        return booksService.findAll()
                .stream()
                .map(bookModelMapper::bookToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookResponseDTO show(@PathVariable("id") int id) {
        return bookModelMapper.bookToDTO(booksService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        bookDTOValidator.validate(bookRequestDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.save(bookModelMapper.DTOtoBook(bookRequestDTO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult, @PathVariable Integer id) {
        bookDTOValidator.validate(bookRequestDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.update(bookModelMapper.DTOtoBook(bookRequestDTO), id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
