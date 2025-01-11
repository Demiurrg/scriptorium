package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.BookRequestDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.services.GenresService;
import com.dolgikh.scriptorium.util.ErrorResponse;
import com.dolgikh.scriptorium.util.validators.GenreDTOValidator;
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
@RequestMapping("/genres")
public class GenresController {
    private final GenresService genresService;
    private final ModelMapper modelMapper;
    private final GenreDTOValidator genreDTOValidator;

    @Autowired
    public GenresController(GenresService genresService, ModelMapper modelMapper, GenreDTOValidator genreDTOValidator) {
        this.genresService = genresService;
        this.modelMapper = modelMapper;
        this.genreDTOValidator = genreDTOValidator;
    }

    @GetMapping()
    public List<GenreDTO> index() {
        return genresService.findAll()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDTO show(@PathVariable Integer id) {
        return modelMapper.map(genresService.findOne(id), GenreDTO.class);
    }

    @GetMapping("/{id}/books")
    public List<BookRequestDTO> getBooksOfGenre(@PathVariable Integer id) {
        Genre genre = genresService.findOne(id);
        return genre.getBooks()
                .stream()
                .map(book -> modelMapper.map(book, BookRequestDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult) {
        genreDTOValidator.validate(genreDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.save(modelMapper.map(genreDTO, Genre.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult, @PathVariable Integer id) {
        genreDTOValidator.validate(genreDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.update(modelMapper.map(genreDTO, Genre.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        genresService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
