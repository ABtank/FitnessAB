package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@ControllerAdvice
public class HeaderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeaderController.class);

    private UserService userService;
    private ExerciseService exerciseService;
    private RoleService roleService;
    private ModeService modeService;
    private TypeService typeService;
    private CategoryService categoryService;
    private RoundService roundService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }

    @ModelAttribute
    public void navCount(Principal principal, Model model) {
        LOGGER.info("-=navCount(Principal principal, Model model )=-");
        if (principal != null) {
            UserDto userDto = userService.findByLogin(principal.getName()).orElseThrow(NotFoundException::new);
            model.addAttribute("usr", userDto);
        }
        model.addAttribute("exercises_count", exerciseService.count());
        model.addAttribute("users_count", userService.count());
        model.addAttribute("roles_count", roleService.count());
        model.addAttribute("modes_count", modeService.count());
        model.addAttribute("types_count", typeService.count());
        model.addAttribute("categories_count", categoryService.count());
        model.addAttribute("rounds_count", roundService.count());

        model.addAttribute("today", Calendar.getInstance());
    }
}
