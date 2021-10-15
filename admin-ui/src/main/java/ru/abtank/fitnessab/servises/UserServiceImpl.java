package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.persist.entities.User;
import ru.abtank.fitnessab.persist.repositories.RoleRepository;
import ru.abtank.fitnessab.persist.repositories.UserRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.UserSpecification;

import java.util.List;
import java.util.Map;
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
    public Optional<UserCreationDto> findByIdForUpdate(Integer id) {
        return userRepository.findById(id).map(mapper::userToUserCreationDto);
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

    @Override
    public Page<UserDto> findAll(Map<String, String> params, PageRequest pageRequest) {
        Specification<User> spec = UserSpecification.trueLiteral();
        if (params.containsKey("check_login_filter") && !params.get("login_filter").isBlank()) {
            spec = spec.and(UserSpecification.loginContains(params.get("login_filter")));
        }
        if (params.containsKey("check_email_filter") && !params.get("email_filter").isBlank()) {
            spec = spec.and(UserSpecification.emailContains(params.get("email_filter")));
        }
        return userRepository.findAll(spec, pageRequest).map(mapper::userToDto);
    }

    @Override
    public List<User> findAll(Specification<User> spec) {
        return userRepository.findAll(spec);
    }
}
