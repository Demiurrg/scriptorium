package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.BooksService;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    private final BooksService booksService;
    private final BookMapper bookMapper;

    @GetMapping
    public List<BookResponseDTO> index() {
        return bookMapper.toDTOList(booksService.findAll());
    }

    @GetMapping("/{id}")
    public BookResponseDTO show(@PathVariable("id") long id) {
        return bookMapper.toDTO(booksService.findOne(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.save(bookMapper.toEntity(bookRequestDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        booksService.update(bookMapper.toEntity(bookRequestDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        booksService.delete(id);
    }
}
