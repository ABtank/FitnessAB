package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/workout_exercise")
public class WorkoutExerciseController {
    private WorkoutService workoutService;
    private ExerciseService exerciseService;
    private TypeService typeService;
    private ModeService modeService;
    private WorkoutExerciseService workoutExerciseService;
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseController.class);

    @Autowired
    public void setWorkoutExerciseService(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Autowired
    public void setWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    @GetMapping
    public String allWorkoutExercise(Model model) {
        LOGGER.info("-=allWorkoutExercise(Model model)=-");
        List<WorkoutExerciseDto> workoutExercises = workoutExerciseService.findAll();
        model.addAttribute("nav_selected", "nav_workouts");
        model.addAttribute("workoutExercises", workoutExercises);
        return "workout_exercises";
    }

    @GetMapping("/{id}")
    public String editWorkoutExercise(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("-=editWorkoutExercise(@PathVariable(\"id\") Integer id, Model model)=-");
        WorkoutExerciseDto workoutExercise = workoutExerciseService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("exercises", exerciseService.findAll());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("modes", modeService.findAll());
        model.addAttribute("nav_selected", "nav_workouts");
        return "workout_exercise";
    }

    @DeleteMapping("{id}/delete")
    public String deleteWorkoutExercise(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=deleteWorkoutExercise(@PathVariable(" + id + ") Integer id, RedirectAttributes redirectAttributes)=-");
        Integer workoutId = workoutService.findById(workoutExerciseService.findById(id).orElseThrow(NotFoundException::new).getWorkoutId()).orElseThrow(NotFoundException::new).getId();
        workoutExerciseService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE WorkoutExercise");
        return "redirect:/workout/"+workoutId;
    }

    @PostMapping("/update")
    public String updateWorkoutExercise(Model model, @ModelAttribute("workoutExercise") @Valid WorkoutExerciseDto workoutExercise, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateWorkoutExercise(@ModelAttribute(\"workoutExercise\") WorkoutExerciseDto workoutExercise, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes)=-");
        System.out.println(workoutExercise);
        String msg;
        if (bindingResult.hasErrors()) {
            LOGGER.info(bindingResult.toString());
            model.addAttribute("exception", bindingResult.toString());
            model.addAttribute("workoutExercise", workoutExercise);
            model.addAttribute("exercises", exerciseService.findAll());
            model.addAttribute("types", typeService.findAll());
            model.addAttribute("modes", modeService.findAll());
            return "workout_exercise";
        }
        msg = (workoutExercise.getId() != null) ? " Susses update WorkoutExercise " : " Susses create workoutExercise ";
        WorkoutExerciseDto savedWorkoutExercise = workoutExerciseService.save(workoutExercise).orElseThrow(NotFoundException::new);
        msg += workoutExercise.getExerciseName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/workout/" + savedWorkoutExercise.getWorkoutId();
    }

}
