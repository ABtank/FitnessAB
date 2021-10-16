package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.CategoryDto;
import ru.abtank.fitnessab.persist.entities.Category;
import ru.abtank.fitnessab.persist.repositories.CategoryRepository;
import ru.abtank.fitnessab.servises.CategoryService;
import ru.abtank.fitnessab.servises.Mapper;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private Mapper mapper;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(mapper::categoryToDto).collect(toList());
    }

    @Override
    public Optional<CategoryDto> findById(Integer id) {
        return categoryRepository.findById(id).map(mapper::categoryToDto);
    }

    @Override
    public Optional<CategoryDto> findByName(String name) {
        return categoryRepository.findByName(name).map(mapper::categoryToDto);
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all Categories");
    }

    @Override
    public Optional<CategoryDto> save(CategoryDto o) {
        Category category = categoryRepository.save(mapper.categoryDtoToCategory(o));
        return findById(category.getId());
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }
}
