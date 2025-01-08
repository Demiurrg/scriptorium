package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.BookRequestDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.services.GenresService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenresController {
    private final GenresService genresService;
    private final ModelMapper modelMapper;

    @Autowired
    public GenresController(GenresService genresService, ModelMapper modelMapper) {
        this.genresService = genresService;
        this.modelMapper = modelMapper;
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
    public ResponseEntity<HttpStatus> save(@RequestBody GenreDTO genreDTO) {
        genresService.save(modelMapper.map(genreDTO, Genre.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody GenreDTO genreDTO, @PathVariable Integer id) {
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        genre.setId(id);
        genresService.save(genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        genresService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
