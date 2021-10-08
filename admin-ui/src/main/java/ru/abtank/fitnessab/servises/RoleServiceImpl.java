package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.RoleDto;
import ru.abtank.fitnessab.persist.entities.Role;
import ru.abtank.fitnessab.persist.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    private RoleRepository roleRepository;
    private Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(mapper::roleToDto).collect(toList());
    }

    @Override
    public Optional<RoleDto> findById(Integer id) {
        return roleRepository.findById(id).map(mapper::roleToDto);
    }

    @Override
    public Optional<RoleDto> findByName(String name) {
        return roleRepository.findByName(name).map(mapper::roleToDto);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = roleRepository.save(mapper.roleDtoToRole(roleDto));
        return mapper.roleToDto(role);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all roles");
    }
}
