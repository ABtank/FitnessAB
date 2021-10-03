package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.persist.entities.User;
import ru.abtank.fitnessab.persist.repositories.RoleRepository;
import ru.abtank.fitnessab.persist.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private Mapper mapper;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::userToDto)
                .collect(toList());
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id).map(mapper::userToDto);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        return userRepository.findByLogin(login).map(mapper::userToDto);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto save(UserCreationDto userCreationDTO) {
        User user = mapper.userCreationDTOtoUser(userCreationDTO);
        userCreationDTO.getRoles()
                .stream()
                .map(role -> roleRepository.findByName(role).get())
                .forEach(user.getRoles()::add);
        return mapper.userToDto(userRepository.save(user));
    }

    @Override
    public void deleteAll(){
        LOGGER.error("Someone decided to delete all Users");
    }
}
