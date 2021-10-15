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
import ru.abtank.fitnessab.dto.RoleDto;
import ru.abtank.fitnessab.dto.UserCreationDto;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.persist.entities.User;
import ru.abtank.fitnessab.servises.RoleService;
import ru.abtank.fitnessab.servises.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user") // localhost:8080/fitness/user
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
   @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //весь список юзеров
    @GetMapping
    public String allUsers(Model model,
                           @RequestParam Map <String,String> params,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort,
                           @RequestParam("direction") Optional<String> direction,
                           Principal principal
    ) {

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(10), direction.isEmpty() ? Sort.Direction.ASC : Sort.Direction.DESC, sort.orElse("id"));
        Page<UserDto> userPage = userService.findAll(params, pageRequest);
        model.addAttribute("usersPage", userPage);

        List<UserDto> userList = userService.findAll();
        model.addAttribute("userList", userList);
        List<RoleDto> roles = roleService.findAll();
        model.addAttribute("user", new UserCreationDto());
        model.addAttribute("roles", roles);

        model.addAttribute("time", getDate());
        model.addAttribute("nav_selected", "nav_users");
        LOGGER.info("GET ALL USERS: " + userPage.stream()
                .map(UserDto::getLogin)
                .collect(Collectors.joining(", ")));
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        UserCreationDto user = userService.findByIdForUpdate(id).orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id, "not Found!"));
        LOGGER.info("EDIT USER: " + user.toString());
        List<RoleDto> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        model.addAttribute("nav_selected", "nav_users");
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(Model model, @ModelAttribute("user") @Valid UserCreationDto user, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        String msg;
        LOGGER.info(" START UPDATE OR INSERT USER: " + user.toString());
        if (bindingResult.hasErrors()) {
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "users";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            bindingResult.rejectValue("matchingPassword", "error.matchingPassword", "пароль не совпал");
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "redirect:/user";
        }
        if (!userService.checkIsUnique(user.getLogin(),user.getEmail(),user.getId())) {
            msg = "Login or email already exists";
            redirectAttributes.addFlashAttribute("exception", msg);
            return (user.getId() != null) ? "redirect:/user/" + user.getId() : "redirect:/user";
        }
        msg = (user.getId() != null) ? " Success update User " : " Success create User ";
        userService.save(user);
        LOGGER.info("SAVE SAVE SAVE");
        msg += user.getLogin();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        LOGGER.info("CREATE USER");
        model.addAttribute("user", new UserCreationDto());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("nav_selected", "ADD_NEW");
        return "user";
    }

    @DeleteMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("DELETE USER id=" + id);
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE User");
        return "redirect:/user";
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}