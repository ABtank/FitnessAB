package ru.abtank.fitnessab.servises;

import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    Optional<UserDto> findById(Integer id);
    Optional<UserDto> findByLogin(String login);
    void deleteById(Integer id);
    UserDto save(UserCreationDto userCreationDTO);
    void deleteAll();
}
