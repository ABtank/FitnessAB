package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.ExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.ExerciseService;

import java.util.List;

@Tag(name = "Exercise resource API", description = "API to operate Exercise resource ...")
@RequestMapping("/api/v1/exercise")
@RestController
public class ExersiceController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExersiceController.class);
    private ExerciseService exerciseService;

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseDto> getAllExercises() {
        LOGGER.info("-=getAllexercises()=-");
        return exerciseService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ExerciseDto findById(@PathVariable("id") Integer id) {
        return exerciseService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseDto create(@RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("-=create(@RequestBody ExerciseDto exerciseDto)=-");
        System.out.println(exerciseDto);
        if(exerciseDto.getName() == null){
            throw new IllegalArgumentException("ERROR: must specify the name of the Exercise");
        }else{
            exerciseDto.setId(null);
        }
        return exerciseService.save(exerciseDto);
    }

    @PutMapping
    public ExerciseDto updateExercise(@RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("-=updateExercise(@RequestBody ExerciseDto exerciseDto)=-");
        if (exerciseDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return exerciseService.save(exerciseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        exerciseService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Exercises=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        exerciseService.deleteById(id);
        return new ResponseEntity<>("Exercise deleted", HttpStatus.OK);
    }
}
