package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.WorkoutExerciseService;

import java.util.Map;
import java.util.Optional;

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
    public Page<WorkoutExerciseDto> getAllWorkoutExercises(
            @RequestParam Map<String, String> params,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("direction") Optional<String> direction
    ) {
        LOGGER.info("-=getAllWorkoutExercises()=-");
        LOGGER.info(String.valueOf(params));
        if(page.isPresent() && page.get() < 1) page = Optional.of(1);
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("ordinal"));
        Page<WorkoutExerciseDto> pages = workoutExerciseService.findAll(params, pageRequest);
        LOGGER.info("pages.isEmpty()="+pages.isEmpty());
        LOGGER.info(pages.toString());
        return pages;
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
        return workoutExerciseService.save(workoutExerciseDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public WorkoutExerciseDto updateWorkoutExercise(@RequestBody WorkoutExerciseDto workoutExerciseDto) {
        LOGGER.info("-=updateWorkoutExercise(@RequestBody WorkoutExerciseDto workoutExerciseDto)=-");
        if (workoutExerciseDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return workoutExerciseService.save(workoutExerciseDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        workoutExerciseService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all WorkoutExercises=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        workoutExerciseService.deleteById(id);
        LOGGER.info("-=WorkoutExercise deleted OK=-");
     }
}
