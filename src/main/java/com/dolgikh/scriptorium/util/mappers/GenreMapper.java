package com.dolgikh.scriptorium.util.mappers;

import com.dolgikh.scriptorium.dto.GenreDTO;
import com.dolgikh.scriptorium.models.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toEntity(GenreDTO genreDTO);

    GenreDTO toDTO(Genre genre);
}
