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
import ru.abtank.fitnessab.dto.TypeDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.TypeService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/type") // localhost:8189/fitnessab/type
public class TypeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TypeController.class);
    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    //весь список юзеров
    @GetMapping
    public String allTypes(Model model) {
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("type", new TypeDto());
        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_types");
        LOGGER.info("GET ALL typeS: ");
        return "types";
    }


    @GetMapping("/{id}")
    public String editType(@PathVariable("id") Integer id, Model model) throws SQLException {
        TypeDto type = typeService.findById(id).orElseThrow(NotFoundException::new);
        LOGGER.info("EDIT type: " + type.getName());
        model.addAttribute("type", type);
        model.addAttribute("nav_selected", "nav_types");
        return "type";
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public String updateType(@ModelAttribute("type") @Valid TypeDto type, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("START UPDATE OR INSERT MODE: " + type.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("exception", bindingResult.toString());
            return "type";
        }
        if (!typeService.checkIsUnique(type.getName(), type.getId())) {
            bindingResult.rejectValue("name", "error.name", "такая type уже есть");
            model.addAttribute("type", type);
            return "type";
        }
        String msg = (type.getId() != null) ? "Susses update type " : "Susses create type ";
        typeService.save(type);
        msg += type.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/type";
    }

    @GetMapping("/create")
    public String createType(Model model) {
        LOGGER.info("CREATE new type");
        model.addAttribute("type", new TypeDto());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "type";
    }

    @DeleteMapping("{id}/delete")
    public String deleteType(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE type id=" + id);
        typeService.deleteById(id);
        return "redirect:/type";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}