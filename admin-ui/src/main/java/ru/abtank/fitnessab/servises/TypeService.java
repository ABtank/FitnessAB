package ru.abtank.fitnessab.servises;

import ru.abtank.fitnessab.dto.TypeDto;

import java.util.Optional;
import java.util.Set;

public interface TypeService {
    Set<TypeDto> findAllDto();
    Optional<TypeDto> findByIdDto(Integer id);
    TypeDto findByIdTypeDto(Integer id);
    Optional<TypeDto> findByName(String name);
    void deleteById(Integer id);
    void save(TypeDto typeDto);
}
