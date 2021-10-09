package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.TypeService;

import java.util.List;

@Tag(name = "Type resource API", description = "API to operate Type resource ...")
@RequestMapping("/api/v1/type")
@RestController
public class TypeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TypeController.class);
    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<TypeDto> getAllTypes() {
        LOGGER.info("-=getAllTypes()=-");
        return typeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TypeDto findById(@PathVariable("id") Integer id) {
        return typeService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TypeDto create(@RequestBody TypeDto typeDto) {
        LOGGER.info("-=create(@RequestBody TypeDto TypeDto)=-");
        System.out.println(typeDto);
        if(typeDto.getName() == null){
            throw new IllegalArgumentException("ERROR: must specify the name of the type");
        }else{
            typeDto.setId(null);
        }
        return typeService.save(typeDto);
    }

    @PutMapping
    public TypeDto updateType(@RequestBody TypeDto typeDto) {
        LOGGER.info("-=updateType(@RequestBody TypeDto typeDto)=-");
        if (typeDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return typeService.save(typeDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        typeService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Types=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        typeService.deleteById(id);
        return new ResponseEntity<>("Type deleted", HttpStatus.OK);
    }
}