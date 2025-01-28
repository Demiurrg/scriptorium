package com.dolgikh.scriptorium.controllers;

import com.dolgikh.scriptorium.dto.books.BookResponseDTO;
import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.services.GenresService;
import com.dolgikh.scriptorium.util.mappers.BookMapper;
import com.dolgikh.scriptorium.util.mappers.GenreMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresController {
    private final GenresService genresService;
    private final GenreMapper genreMapper;
    private final BookMapper bookMapper;

    @GetMapping
    public Page<GenreDTO> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return genresService.findAll(PageRequest.of(page, size)).map(genreMapper::toDTO);
    }

    @GetMapping("/{id}")
    public GenreDTO show(@PathVariable long id) {
        return genreMapper.toDTO(genresService.findOne(id));
    }

    @GetMapping("/{id}/books")
    public Page<BookResponseDTO> getBooksByGenre(@PathVariable long id,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return genresService.findBooksByGenre(id, PageRequest.of(page, size)).map(bookMapper::toDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid GenreDTO genreDTO) {
        genresService.save(genreMapper.toEntity(genreDTO));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid GenreDTO genreDTO, @PathVariable long id) {
        genresService.update(genreMapper.toEntity(genreDTO), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        genresService.delete(id);
    }
}
