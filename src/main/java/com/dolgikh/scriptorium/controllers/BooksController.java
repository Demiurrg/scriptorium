package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.BooksService;
import com.dolgikh.scriptorium.util.validators.BookDTOValidator;
import com.dolgikh.scriptorium.util.BookModelMapper;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    private final BooksService booksService;
    private final BookModelMapper bookModelMapper;
    private final BookDTOValidator bookDTOValidator;

    @GetMapping
    public List<BookResponseDTO> index() {
        return booksService.findAll()
                .stream()
                .map(bookModelMapper::bookToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookResponseDTO show(@PathVariable("id") long id) {
        return bookModelMapper.bookToDTO(booksService.findOne(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        bookDTOValidator.validate(bookRequestDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.save(bookModelMapper.DTOtoBook(bookRequestDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult, @PathVariable long id) {
        bookDTOValidator.validate(bookRequestDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.update(bookModelMapper.DTOtoBook(bookRequestDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        booksService.delete(id);
    }
}
