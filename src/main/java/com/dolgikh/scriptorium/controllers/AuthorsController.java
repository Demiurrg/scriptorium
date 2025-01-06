package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.AuthorDTO;
import com.dolgikh.scriptorium.dto.BookDTO;
import com.dolgikh.scriptorium.models.Author;
import com.dolgikh.scriptorium.services.AuthorsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorsService authorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorsController(AuthorsService authorsService, ModelMapper modelMapper) {
        this.authorsService = authorsService;
        this.modelMapper = modelMapper;
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
    public List<BookDTO> getBooksOfAuthor(@PathVariable Integer id) {
        return authorsService.findOne(id).getBooks()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody AuthorDTO authorDTO) {
        authorsService.save(modelMapper.map(authorDTO, Author.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        Author author = modelMapper.map(authorDTO, Author.class);
        author.setId(id);
        authorsService.save(author);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        authorsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
