package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.WorkoutDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.WorkoutService;

import java.util.List;

@Tag(name = "Workout resource API", description = "API to operate Workout resource ...")
@RequestMapping("/api/v1/workout")
@RestController
public class WorkoutRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutRestController.class);
    private WorkoutService workoutService;

    @Autowired
    public void setWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<WorkoutDto> getAllWorkouts() {
        LOGGER.info("-=getAllWorkouts()=-");
        return workoutService.findAll();
    }

    @GetMapping(value = "/{id}")
    public WorkoutDto findById(@PathVariable("id") Integer id) {
        return workoutService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutDto create(@RequestBody WorkoutDto workoutDto) {
        LOGGER.info("-=create(@RequestBody WorkoutDto workoutDto)=-");
        System.out.println(workoutDto);
        if(workoutDto.getName() == null){
            throw new IllegalArgumentException("ERROR: must specify the name of the Workout");
        }else{
            workoutDto.setId(null);
        }
        return workoutService.save(workoutDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public WorkoutDto updateWorkout(@RequestBody WorkoutDto workoutDto) {
        LOGGER.info("-=updateWorkout(@RequestBody WorkoutDto workoutDto)=-");
        if (workoutDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return workoutService.save(workoutDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        workoutService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Workouts=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        workoutService.deleteById(id);
        LOGGER.info("-=Workout deleted OK=-");
    }
}
