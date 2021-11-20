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
import ru.abtank.fitnessab.dto.RoleDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.RoleService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/role") // localhost:8080/fitness/user
public class RoleController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);


    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //весь список юзеров
    @GetMapping
    public String allRoles(Model model) {
        LOGGER.info("-=allRoles(Model model)=-");
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("role", new RoleDto());
        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_roles");
        return "roles";
    }


    @GetMapping("/{id}")
    public String editRole(@PathVariable("id") Integer id, Model model) throws SQLException {
        LOGGER.info("-=editRole(@PathVariable(\"id\") Integer id, Model model)=-");
        RoleDto roleDto = roleService.findById(id).orElseThrow(()->new NotFoundException("Role", id," not Found!"));
        model.addAttribute("role", roleDto);
        model.addAttribute("nav_selected", "nav_roles");
        return "role";
    }

    @GetMapping("/create")
    public String createRole(Model model) {
        LOGGER.info("-=createRole(Model model)=-");
        model.addAttribute("role", new RoleDto());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "role";
    }

    @DeleteMapping("{id}/delete")
    public String deleteRole(@PathVariable("id") Integer id) {
        LOGGER.info("-=deleteRole(@PathVariable(\"id\") Integer id)=-");
        roleService.deleteById(id);
        return "redirect:/role";
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public String updateRole(@ModelAttribute("role") @Valid RoleDto roleDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateRole(@ModelAttribute(\"role\") @Valid RoleDto roleDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)=-");
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", roleDto);
            model.addAttribute("exception", bindingResult.toString());
            return "role";
        }
        if (roleService.findByName(roleDto.getName()).isPresent()) {
            bindingResult.rejectValue("name", "error.name", "такая роль уже есть");
            model.addAttribute("role", roleDto);
            return "role";
        }
        String msg = (roleDto.getId() != null) ? "Susses update role " : "Susses create role ";
        roleService.save(roleDto);
        msg += roleDto.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/role";
    }

    public String getDate() {
        LOGGER.info("-=getDate()=-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}