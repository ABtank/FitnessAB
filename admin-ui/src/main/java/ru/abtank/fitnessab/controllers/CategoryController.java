package ru.abtank.fitnessab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.fitnessab.dto.CategoryDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.CategoryService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String allCategory(Model model) {
        LOGGER.info("-=allCategory(Model model)=-");
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute("nav_selected", "nav_categories");
        model.addAttribute("category", new CategoryDto());
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("-=editCategory(@PathVariable(\"id\") Integer id, Model model)=-");
        CategoryDto category = categoryService.findById(id).orElseThrow(NotFoundException::new);
        LOGGER.info("CREATOR Category: " + category.getCreatorLogin());
        model.addAttribute("category", category);
        model.addAttribute("nav_selected", "nav_categories");
        return "category";
    }

    @DeleteMapping("{id}/delete")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=deleteCategory(@PathVariable("+id+") Integer id, RedirectAttributes redirectAttributes)=-");
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Success DELETE Category");
        return "redirect:/category";
    }

    @PostMapping("/update")
    public String updateCategory(Model model, @ModelAttribute("category") @Valid CategoryDto category, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        LOGGER.info("-=updateCategory(@ModelAttribute(\"category\") CategoryDto category, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes)=-");
        System.out.println(category);
        String msg;
         if (bindingResult.hasErrors()) {
            LOGGER.info(bindingResult.toString());
            model.addAttribute("exception", bindingResult.toString());
            model.addAttribute("category", category);
            return "category";
        }
        msg = (category.getId() != null) ? " Susses update Category " : " Susses create category ";
        category.setCreatorLogin(principal.getName());
        categoryService.save(category);
        msg += category.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/category";
    }

}
