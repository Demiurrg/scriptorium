package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.services.GenresService;
import com.dolgikh.scriptorium.util.exceptions.ErrorResponse;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import com.dolgikh.scriptorium.util.mappers.GenreMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresController {
    private final GenresService genresService;
    private final GenreMapper genreMapper;
    private final BookMapper bookMapper;

    @GetMapping
    public List<GenreDTO> index() {
        return genresService.findAll()
                .stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDTO show(@PathVariable long id) {
        return genreMapper.toDTO(genresService.findOne(id));
    }

    @GetMapping("/{id}/books")
    public List<BookResponseDTO> getBooksOfGenre(@PathVariable long id) {
        return bookMapper.toDTOList(genresService.findBooksOfGenre(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.save(genreMapper.toEntity(genreDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid GenreDTO genreDTO, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(ErrorResponse.printFieldErrors(bindingResult.getFieldErrors()));

        genresService.update(genreMapper.toEntity(genreDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        genresService.delete(id);
    }
}
