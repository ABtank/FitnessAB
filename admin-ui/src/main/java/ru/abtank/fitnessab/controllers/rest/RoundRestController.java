package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.RoundDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.RoundService;

import java.util.Date;
import java.util.List;

@Tag(name = "Round resource API", description = "API to operate Round resource ...")
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
    public List<RoundDto> getAllRounds() {
        LOGGER.info("-=getAllRounds()=-");
        return roundService.findAll();
    }

    @GetMapping(value = "/{id}")
    public RoundDto findById(@PathVariable("id") Integer id) {
        return roundService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoundDto create(@RequestBody RoundDto roundDto) {
        LOGGER.info("-=create(@RequestBody RoundDto roundDto)=-");
        if(roundDto.getWorkout().getId() == null || roundDto.getExercise().getId() == null){
            throw new IllegalArgumentException("ERROR: must indicated workout and exercise of the Round");
        }else{
            roundDto.setId(null);
            roundDto.setSessionDate(new Date());
        }
        return roundService.save(roundDto);
    }

    @PutMapping
    public RoundDto updateRound(@RequestBody RoundDto roundDto) {
        LOGGER.info("-=updateRound(@RequestBody RoundDto roundDto)=-");
        if (roundDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return roundService.save(roundDto);
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
