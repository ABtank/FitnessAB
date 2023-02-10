package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.RoundDto;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.RoundService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/v1/round")
@RestController
public class RoundRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoundRestController.class);
    private RoundService roundService;

    @Autowired
    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping
    public Page<RoundDto> getAllRounds(@RequestParam @Parameter(description = "Указать {\"\",\"\"}") Map<String, String> params,
                                       @RequestParam("page") @Parameter(example = "1") Optional<Integer> page,
                                       @RequestParam("size") @Parameter(example = "10") Optional<Integer> size,
                                       @RequestParam("sort") @Parameter(example = "createDate") Optional<String> sort,
                                       @RequestParam("direction") @Parameter(example = "DESC") Optional<String> direction) {
        LOGGER.info("-=getAllRounds()=-");
        if (page.isPresent() && page.get() < 1) page = Optional.of(1);
        Sort s = sort.isPresent() ? Sort.by(Sort.Direction.valueOf(direction.orElse("DESC")), sort.orElse("createDate")) : Sort.unsorted();
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), s);
        Page<RoundDto> pages = roundService.findAll(params, pageRequest);
        return pages;
    }

    @GetMapping(value = "/{id}")
    public RoundDto findById(@PathVariable("id") Integer id) {
        return roundService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoundDto create(@RequestBody RoundDto roundDto) {
        LOGGER.info("-=create(@RequestBody RoundDto roundDto)=-");
        if(roundDto.getWorkoutExerciseId() == null){
            throw new IllegalArgumentException("ERROR: must WorkoutExerciseId of the Round");
        }else{
            roundDto.setId(null);
            roundDto.setSessionDate(new Date());
        }
        return roundService.save(roundDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public RoundDto updateRound(@RequestBody RoundDto roundDto) {
        LOGGER.info("-=updateRound(@RequestBody RoundDto roundDto)=-");
        if (roundDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return roundService.save(roundDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        roundService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Rounds=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        roundService.deleteById(id);
        return new ResponseEntity<>("Round deleted", HttpStatus.OK);
    }
}
