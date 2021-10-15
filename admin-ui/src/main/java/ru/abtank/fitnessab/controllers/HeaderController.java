package ru.abtank.fitnessab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.ExerciseService;
import ru.abtank.fitnessab.servises.ModeService;
import ru.abtank.fitnessab.servises.RoleService;
import ru.abtank.fitnessab.servises.UserService;

import java.security.Principal;

@ControllerAdvice
public class HeaderController {

    private UserService userService;
    private ExerciseService exerciseService;
    private RoleService roleService;
    private ModeService modeService;

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

    @ModelAttribute
    public void nav(Principal principal, Model model ) {
        if (principal != null) {
            UserDto userDto = userService.findByLogin(principal.getName()).orElseThrow(NotFoundException::new);
            model.addAttribute("usr", userDto);
            model.addAttribute("exercises_count", exerciseService.count());
            model.addAttribute("users_count", userService.count());
            model.addAttribute("roles_count", roleService.count());
            model.addAttribute("modes_count", modeService.count());
        }
    }
}
