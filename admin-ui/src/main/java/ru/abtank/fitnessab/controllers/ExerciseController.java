package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.*;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.CategoryService;
import ru.abtank.fitnessab.servises.ExerciseService;
import ru.abtank.fitnessab.servises.TypeService;
import ru.abtank.fitnessab.servises.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {
    private ExerciseService exerciseService;
    private CategoryService categoryService;
    private TypeService typeService;
    private UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allExercise(Model model) {
        LOGGER.info("-=allExercise(Model model)=-");
        List<ExerciseDto> exercises = exerciseService.findAll();
        LOGGER.info("//////////////" + exercises.get(0));
        model.addAttribute("nav_selected", "nav_exercises");
        model.addAttribute("exercise", new ExerciseDto());
        model.addAttribute("exercises", exercises);
        return "exercises";
    }

    @GetMapping("/{id}")
    public String editExercise(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("-=editExercise(@PathVariable(\"id\") Integer id, Model model)=-");
        ExerciseDto exercise = exerciseService.findById(id).orElseThrow(NotFoundException::new);
        LOGGER.info("CREATOR Exercise: " + exercise.getCreator().getLogin());
        model.addAttribute("exercise", exercise);
        model.addAttribute("nav_selected", "nav_exercises");
        return "exercise";
    }

    @DeleteMapping("{id}/delete")
    public String deleteExercise(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=deleteExercise(@PathVariable("+id+") Integer id, RedirectAttributes redirectAttributes)=-");
        exerciseService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE Exercise");
        return "redirect:/exercise";
    }

    @PostMapping("/update")
    public String updateExercise(Model model, @ModelAttribute("exercise") @Valid ExerciseDto exercise, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateExercise(@ModelAttribute(\"exercise\") ExerciseDto exercise, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes)=-");
        String msg;
        UserDto creator = userService.findByLogin(principal.getName()).orElseThrow(NotFoundException::new);
        if (bindingResult.hasErrors()) {
            LOGGER.info(bindingResult.toString());
            model.addAttribute("exception", bindingResult.toString());
            model.addAttribute("exercise", exercise);
            return "exercise";
        }
        msg = (exercise.getId() != null) ? creator.getLogin() + " " + creator.getId() + " Susses update Exercise " : creator.getLogin() + " " + creator.getId() + " Susses create exercise ";
        exercise.setCreator(new CreatorDto(creator));
        exerciseService.save(exercise);
        msg += exercise.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/exercise";
    }

    @ModelAttribute("types")
    public List<TypeDto> types(){
        return typeService.findAll();
    }

    @ModelAttribute("categories")
    public List<CategoryDto> categories(){
        return categoryService.findAll();
    }
}
