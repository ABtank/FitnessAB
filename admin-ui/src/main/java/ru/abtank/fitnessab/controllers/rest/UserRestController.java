package ru.abtank.fitnessab.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.UserService;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        LOGGER.info("-=getAllUsers()=-");
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public UserDto findById(@PathVariable("id") Integer id) {
        return userService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreationDto userDTO) {
        LOGGER.info("-=create(@RequestBody UserCreationDTO userDTO)=-");
        System.out.println(userDTO);
        if(!userDTO.getPassword().equals(userDTO.getMatchingPassword())){
            throw new IllegalArgumentException("password not matching");
        }else{
            userDTO.setId(null);
        }
        return userService.save(userDTO).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserCreationDto userDTO) {
        LOGGER.info("-=updateUser(@RequestBody UserCreationDTO userDTO)=-");
        if (userDTO.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return userService.save(userDTO).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        userService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Users=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        userService.deleteById(id);
        LOGGER.info("-=OK=-");
    }
}
