package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.WorkoutDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.UserService;
import ru.abtank.fitnessab.servises.WorkoutService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/workout")
public class WorkoutController {
    private WorkoutService workoutService;
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);

    @Autowired
    public void setWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public String allWorkout(Model model) {
        LOGGER.info("-=allWorkout(Model model)=-");
        List<WorkoutDto> workouts = workoutService.findAll();
        model.addAttribute("nav_selected", "nav_workouts");
        model.addAttribute("workout", new WorkoutDto());
        model.addAttribute("workouts", workouts);
        return "workouts";
    }

    @GetMapping("/{id}")
    public String editWorkout(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("-=editWorkout(@PathVariable(\"id\") Integer id, Model model)=-");
        WorkoutDto workout = workoutService.findById(id).orElseThrow(NotFoundException::new);
        LOGGER.info("CREATOR Workout: " + workout.getCreatorLogin());
        model.addAttribute("workout", workout);
        model.addAttribute("nav_selected", "nav_workouts");
        return "workout";
    }

    @DeleteMapping("{id}/delete")
    public String deleteWorkout(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=deleteWorkout(@PathVariable("+id+") Integer id, RedirectAttributes redirectAttributes)=-");
        workoutService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE Workout");
        return "redirect:/workout";
    }

    @PostMapping("/update")
    public String updateWorkout(Model model, @ModelAttribute("workout") @Valid WorkoutDto workout, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateWorkout(@ModelAttribute(\"workout\") WorkoutDto workout, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes)=-");
        String msg;
         if (bindingResult.hasErrors()) {
            LOGGER.info(bindingResult.toString());
            model.addAttribute("exception", bindingResult.toString());
            model.addAttribute("workout", workout);
            return "workout";
        }
        msg = (workout.getId() != null) ? " Susses update Workout " : " Susses create workout ";
        workout.setCreatorLogin(principal.getName());
        workoutService.save(workout);
        msg += workout.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/workout";
    }

}
