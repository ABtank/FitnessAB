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
import ru.abtank.fitnessab.dto.ExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.ExerciseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Exercise resource API", description = "API to operate Exercise resource ...")
@RequestMapping("/api/v1/exercise")
@RestController
public class ExersiceRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExersiceRestController.class);
    private ExerciseService exerciseService;

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public Page<ExerciseDto> getAllExercises(
            @RequestParam Map<String,String> params,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("direction") Optional<String> direction
    ) {
        LOGGER.info("-=getAllexercises()=-");
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("id"));
        return exerciseService.findAll(params,pageRequest);
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
        return exerciseService.save(exerciseDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public ExerciseDto updateExercise(@RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("-=updateExercise(@RequestBody ExerciseDto exerciseDto)=-");
        if (exerciseDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return exerciseService.save(exerciseDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        exerciseService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Exercises=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        exerciseService.deleteById(id);
//        return new ResponseEntity<>("Exercise deleted", HttpStatus.OK);
        LOGGER.info("-=Exercise deleted OK=-");
    }
}
