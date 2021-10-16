package ru.abtank.fitnessab.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.fitnessab.dto.CategoryDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.servises.CategoryService;

import java.util.List;

@Tag(name = "Category resource API", description = "API to operate Category resource ...")
@RequestMapping("/api/v1/category")
@RestController
public class CategoryRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryRestController.class);
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        LOGGER.info("-=getAllCategories()=-");
        return categoryService.findAll();
    }

    @GetMapping(value = "/{id}")
    public CategoryDto findById(@PathVariable("id") Integer id) {
        return categoryService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        LOGGER.info("-=create(@RequestBody CategoryDto CategoryDto)=-");
        System.out.println(categoryDto);
        if(categoryDto.getName() == null){
            throw new IllegalArgumentException("ERROR: must specify the name of the Category");
        }else{
            categoryDto.setId(null);
        }
        return categoryService.save(categoryDto).orElseThrow(NotFoundException::new);
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        LOGGER.info("-=updateCategory(@RequestBody CategoryDto categoryDto)=-");
        if (categoryDto.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return categoryService.save(categoryDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        categoryService.deleteAll();
        return new ResponseEntity<>("-=You cannot delete all Categories=-", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        LOGGER.info("-=delete(@PathVariable Integer id)=-");
        categoryService.deleteById(id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }
}
