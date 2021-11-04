package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.abtank.fitnessab.dto.CounterDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.CounterService;
import ru.abtank.fitnessab.servises.UserService;

import java.security.Principal;
import java.util.Calendar;

@ControllerAdvice
public class HeaderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeaderController.class);

    private UserService userService;
    private CounterService counterService;

    @Autowired
    public void setCounterService(CounterService counterService) {
        this.counterService = counterService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void navCount(Principal principal, Model model) {
        LOGGER.info("-=navCount(Principal principal, Model model )=-");
        CounterDto allCount = counterService.allCount();
        System.out.println(allCount);
        if (principal != null) {
            UserDto userDto = userService.findByLogin(principal.getName()).orElseThrow(NotFoundException::new);
            model.addAttribute("usr", userDto);
            CounterDto allMyCount = counterService.allMyCount(userDto.getId());
            System.out.println("allMyCount = "+allMyCount);
            System.out.println("allCount = "+allCount);
        model.addAttribute("exercises_my_count", allMyCount.getExercise());
        model.addAttribute("workouts_my_count", allMyCount.getWorkout());
        model.addAttribute("users_my_count", allMyCount.getUser());
        model.addAttribute("roles_my_count", allMyCount.getRole());
        model.addAttribute("modes_my_count", allMyCount.getMode());
        model.addAttribute("types_my_count", allMyCount.getType());
        model.addAttribute("categories_my_count", allMyCount.getCategory());
        model.addAttribute("rounds_my_count", allMyCount.getRound());
    }
        model.addAttribute("exercises_count", allCount.getExercise());
        model.addAttribute("workouts_count", allCount.getWorkout());
        model.addAttribute("users_count", allCount.getUser());
        model.addAttribute("roles_count", allCount.getRole());
        model.addAttribute("modes_count", allCount.getMode());
        model.addAttribute("types_count", allCount.getType());
        model.addAttribute("categories_count", allCount.getCategory());
        model.addAttribute("rounds_count", allCount.getRound());

        model.addAttribute("today", Calendar.getInstance());
    }
}
