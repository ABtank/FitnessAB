package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.RoleDto;
import ru.abtank.fitnessab.persist.entities.Role;
import ru.abtank.fitnessab.persist.repositories.RoleRepository;
import ru.abtank.fitnessab.servises.RoleService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(obj -> modelMapper.map(obj, RoleDto.class)).collect(toList());
    }

    @Override
    public Optional<RoleDto> findById(Integer id) {
        return roleRepository.findById(id).map(obj -> modelMapper.map(obj, RoleDto.class));
    }

    @Override
    public Optional<RoleDto> findByName(String name) {
        return roleRepository.findByName(name).map(obj -> modelMapper.map(obj, RoleDto.class));
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<RoleDto> save(RoleDto roleDto) {
        Role role = roleRepository.save(modelMapper.map(roleDto, Role.class));
        return findById(role.getId());
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all roles");
    }
}
