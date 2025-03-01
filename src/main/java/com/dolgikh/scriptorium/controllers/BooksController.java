package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookRequestDTO;
import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.services.BooksService;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    private final BooksService booksService;
    private final BookMapper bookMapper;

    @GetMapping
    public Page<BookResponseDTO> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return booksService.findAll(PageRequest.of(page, size)).map(bookMapper::toDTO);
    }

    @GetMapping("/{id}")
    public BookResponseDTO show(@PathVariable("id") long id) {
        return bookMapper.toDTO(booksService.findOne(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        booksService.save(bookMapper.toEntity(bookRequestDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid BookRequestDTO bookRequestDTO, @PathVariable long id) {
        booksService.update(bookMapper.toEntity(bookRequestDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        booksService.delete(id);
    }
}
