package ru.abtank.fitnessab.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.ModeService;

import java.util.List;

@RequestMapping("/api/v1/mode")
@RestController
public class ModeRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ModeRestController.class);
    private ModeService modeService;

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    @GetMapping
    public List<ModeDto> getAllModes() {
        LOGGER.info("-=getAllModes()=-");
        return modeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ModeDto findById(@PathVariable("id") Integer id) {
        return modeService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModeDto create(@RequestBody ModeDto modeDto) {
        LOGGER.info("-=create(@RequestBody ModeDto ModeDto)=-");
        System.out.println(modeDto);
        modeDto.setId(null);
        return modeService.save(modeDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public ModeDto updateMode(@RequestBody ModeDto modeDto) {
        LOGGER.info("-=updateMode(@RequestBody ModeDto modeDto)=-");
        if (modeDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return modeService.save(modeDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        modeService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Modes=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        modeService.deleteById(id);
        LOGGER.info("-=Mode deleted OK=-");
    }
}
