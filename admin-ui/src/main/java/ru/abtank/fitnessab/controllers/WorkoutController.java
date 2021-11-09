package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.ExerciseDto;
import ru.abtank.fitnessab.dto.WorkoutDto;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workout")
public class WorkoutController {
    private WorkoutService workoutService;
    private ExerciseService exerciseService;
    private TypeService typeService;
    private ModeService modeService;
    private WorkoutExerciseService workoutExerciseService;
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);

    @Autowired
    public void setWorkoutExerciseService(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

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

        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "ordinal");
        Map<String, String> params = new HashMap<>();
        params.put("workoutId", String.valueOf(workout.getId()));
        Page<WorkoutExerciseDto> workoutExercisesPage = workoutExerciseService.findAll(params, pageRequest);

        model.addAttribute("workout", workout);
        model.addAttribute("exercises", exerciseService.findAll());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("modes", modeService.findAll());
        model.addAttribute("workoutExercise", new WorkoutExerciseDto(workout.getId()));
        model.addAttribute("workoutExercisesPage", workoutExercisesPage);
        model.addAttribute("nav_selected", "nav_workouts");
        return "workout";
    }

    @DeleteMapping("{id}/delete")
    public String deleteWorkout(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=deleteWorkout(@PathVariable(" + id + ") Integer id, RedirectAttributes redirectAttributes)=-");
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
