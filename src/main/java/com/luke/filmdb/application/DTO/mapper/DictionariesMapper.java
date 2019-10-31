package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.dictionaries.SimpleDictionaryDTO;
import com.luke.filmdb.application.model.dictionaries.GenresDictionary;
import com.luke.filmdb.application.model.dictionaries.PersonRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionariesMapper {

    GenresDictionary genresDictionaryToSimpleDictionaryDTO(SimpleDictionaryDTO simpleDictionaryDTO);

    PersonRole personRoleToSimpleDictionaryDTO(SimpleDictionaryDTO simpleDictionaryDTO);
}
