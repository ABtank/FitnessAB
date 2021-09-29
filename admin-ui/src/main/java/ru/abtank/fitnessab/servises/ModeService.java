package ru.abtank.fitnessab.servises;

import ru.abtank.fitnessab.dto.ModeDto;

import java.util.Optional;
import java.util.Set;

public interface ModeService {
    Set<ModeDto> findAllDto();
    Optional<ModeDto> findByIdDto(Integer id);
    Optional<ModeDto> findByName(String name);
    void deleteById(Integer id);
    void save(ModeDto modeDto);
}
