package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.models.Genre;
import com.dolgikh.scriptorium.services.GenresService;
import com.dolgikh.scriptorium.util.BookModelMapper;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import com.dolgikh.scriptorium.util.validators.GenreDTOValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenresController {
    private final GenresService genresService;
    private final ModelMapper modelMapper;
    private final BookModelMapper bookModelMapper;
    private final GenreDTOValidator genreDTOValidator;

    @Autowired
    public GenresController(GenresService genresService, ModelMapper modelMapper, BookModelMapper bookModelMapper, GenreDTOValidator genreDTOValidator) {
        this.genresService = genresService;
        this.modelMapper = modelMapper;
        this.bookModelMapper = bookModelMapper;
        this.genreDTOValidator = genreDTOValidator;
    }

    @GetMapping
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
    public List<BookResponseDTO> getBooksOfGenre(@PathVariable Integer id) {
        return bookModelMapper.allBooksToDTO(genresService.findBooksOfGenre(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult) {
        genreDTOValidator.validate(genreDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.save(modelMapper.map(genreDTO, Genre.class));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult, @PathVariable Integer id) {
        genreDTOValidator.validate(genreDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.update(modelMapper.map(genreDTO, Genre.class), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        genresService.delete(id);
    }
}
