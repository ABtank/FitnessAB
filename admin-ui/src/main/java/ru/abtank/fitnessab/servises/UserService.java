package ru.abtank.fitnessab.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.persist.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    Optional<UserDto> findById(Integer id);
    Optional<UserDto> findByLogin(String login);
    void deleteById(Integer id);
    Optional<UserDto> save(UserCreationDto userCreationDTO);
    void deleteAll();
    Optional<UserCreationDto> findByIdForUpdate(Integer id);

    Page<UserDto> findAll(Map<String, String> params, PageRequest pageRequest);

    List<UserDto> findAll(Specification<User> spec);
    boolean checkIsUnique(String login, String email, Integer id);
    long count();
}
