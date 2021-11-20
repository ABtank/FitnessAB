package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.RoleDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.RoleService;

import java.util.List;

@Tag(name = "Role resource API", description = "API to operate Role resource ...")
@RequestMapping("/api/v1/role")
@RestController
public class RoleRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoleRestController.class);
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {
        LOGGER.info("-=getAllRoles()=-");
        return roleService.findAll();
    }

    @GetMapping(value = "/{id}")
    public RoleDto findById(@PathVariable("id") Integer id) {
        return roleService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@RequestBody RoleDto roleDto) {
        LOGGER.info("-=create(@RequestBody RoleDto RoleDto)=-");
        System.out.println(roleDto);
        roleDto.setId(null);
        return roleService.save(roleDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public RoleDto updateRole(@RequestBody RoleDto roleDto) {
        LOGGER.info("-=updateRole(@RequestBody RoleDto roleDto)=-");
        if (roleDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return roleService.save(roleDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        roleService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all roles=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        roleService.deleteById(id);
        LOGGER.info("-=OK=-");
    }
}
