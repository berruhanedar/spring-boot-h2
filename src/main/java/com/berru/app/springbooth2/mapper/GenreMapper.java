package com.berru.app.springbooth2.mapper;

import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.dto.UpdateGenreRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(source = "musics", target = "musics")
    GenreDTO toDto(Genre genre);

    Genre toEntity(NewGenreRequestDTO dto);

    void updateGenreFromDto(UpdateGenreRequestDTO dto, @MappingTarget Genre genre);
}
