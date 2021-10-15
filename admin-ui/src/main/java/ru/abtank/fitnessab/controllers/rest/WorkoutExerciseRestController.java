package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.WorkoutExerciseService;

import java.util.List;

@Tag(name = "WorkoutExercise resource API", description = "API to operate WorkoutExercise resource ...")
@RequestMapping("/api/v1/workout_exercise")
@RestController
public class WorkoutExerciseRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseRestController.class);
    private WorkoutExerciseService workoutExerciseService;

    @Autowired
    public void setWorkoutExerciseService(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @GetMapping
    public List<WorkoutExerciseDto> getAllWorkoutExercises() {
        LOGGER.info("-=getAllWorkoutExercises()=-");
        return workoutExerciseService.findAll();
    }

    @GetMapping("/{id}")
    public WorkoutExerciseDto findById(@PathVariable("id") Integer id) {
        return workoutExerciseService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutExerciseDto create(@RequestBody WorkoutExerciseDto workoutExerciseDto) {
        LOGGER.info("-=create(@RequestBody WorkoutExerciseDto WorkoutExerciseDto)=-");
        workoutExerciseDto.setId(null);
        return workoutExerciseService.save(workoutExerciseDto);
    }

    @PutMapping
    public WorkoutExerciseDto updateWorkoutExercise(@RequestBody WorkoutExerciseDto workoutExerciseDto) {
        LOGGER.info("-=updateWorkoutExercise(@RequestBody WorkoutExerciseDto workoutExerciseDto)=-");
        if (workoutExerciseDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return workoutExerciseService.save(workoutExerciseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        workoutExerciseService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all WorkoutExercises=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        workoutExerciseService.deleteById(id);
        return new ResponseEntity<>("WorkoutExercise deleted", HttpStatus.OK);
    }
}
