package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.BookDTO;
import com.dolgikh.scriptorium.models.Book;
import com.dolgikh.scriptorium.services.BooksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final ModelMapper modelMapper;

    @Autowired
    public BooksController(BooksService booksService, ModelMapper modelMapper) {
        this.booksService = booksService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<BookDTO> index() {
        return booksService.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO show(@PathVariable("id") int id) {
        return modelMapper.map(booksService.findOne(id), BookDTO.class);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody BookDTO bookDTO) {
        booksService.save(modelMapper.map(bookDTO, Book.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody BookDTO bookDTO, @PathVariable Integer id) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book.setId(id);
        booksService.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
