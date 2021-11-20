package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.ModeDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.ModeService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/mode") // localhost:8189/fitnessab/mode
public class ModeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModeController.class);
    private ModeService modeService;

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    //весь список юзеров
    @GetMapping
    public String allModes(Model model) {
        LOGGER.info("-=allModes(Model model)=-");
        model.addAttribute("modes", modeService.findAll());
        model.addAttribute("mode", new ModeDto());
        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_modes");
        return "modes";
    }


    @GetMapping("/{id}")
    public String editMode(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("-=editMode(@PathVariable(\"id\") Integer id, Model model)=-");
        ModeDto mode = modeService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("mode", mode);
        model.addAttribute("nav_selected", "nav_modes");
        return "mode";
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public String updateMode(@ModelAttribute("mode") @Valid ModeDto mode, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateMode(@ModelAttribute(\"mode\") @Valid ModeDto mode, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)=-");
        if (bindingResult.hasErrors()) {
            model.addAttribute("exception", bindingResult.toString());
            return "mode";
        }
        if (!modeService.checkIsUnique(mode.getName(), mode.getId())) {
            bindingResult.rejectValue("name", "error.name", "такая mode уже есть");
            model.addAttribute("mode", mode);
            return "mode";
        }
        String msg = (mode.getId() != null) ? "Susses update mode " : "Susses create mode ";
        modeService.save(mode);
        msg += mode.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/mode";
    }

    @GetMapping("/create")
    public String createMode(Model model) {
        LOGGER.info("-=createMode(Model model)=-");
        model.addAttribute("mode", new ModeDto());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "mode";
    }

    @DeleteMapping("{id}/delete")
    public String deleteMode(@PathVariable("id") Integer id) {
        LOGGER.info("-=deleteMode(@PathVariable(\"id\") Integer id)=-");
        modeService.deleteById(id);
        return "redirect:/mode";
    }

    public String getDate() {
        LOGGER.info("-=getDate()=-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}