package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.ExerciseDto;
import ru.abtank.fitnessab.persist.entities.Exercise;
import ru.abtank.fitnessab.persist.repositories.CategoryRepository;
import ru.abtank.fitnessab.persist.repositories.ExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.TypeRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.ExerciseSpecification;
import ru.abtank.fitnessab.servises.ExerciseService;
import ru.abtank.fitnessab.servises.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExerciseServiceImpl.class);
    private Mapper mapper;
    private ExerciseRepository exerciseRepository;
    private CategoryRepository categoryRepository;
    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setExerciseRepository(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<ExerciseDto> findAll() {
        LOGGER.info("-=findAll()=-");
        return exerciseRepository.findAll().stream().map(obj -> modelMapper.map(obj, ExerciseDto.class)).collect(toList());
    }

    @Override
    public Optional<ExerciseDto> findById(Integer id) {
        LOGGER.info("-=findById(Integer id)=-");
        return exerciseRepository.findById(id).map(obj -> modelMapper.map(obj, ExerciseDto.class));
    }

    @Override
    public Optional<ExerciseDto> findByName(String name) {
        LOGGER.info("-=findByName(String name)=-");
        return exerciseRepository.findByName(name).map(obj -> modelMapper.map(obj, ExerciseDto.class));
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.info("-=deleteById(Integer id)=-");
        exerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("-=deleteAll()=-");
        LOGGER.error("Someone decided to delete all Exercises");
    }

    @Override
    public Optional<ExerciseDto> save(ExerciseDto o) {
        LOGGER.info("-=save(ExerciseDto o)=-");
        Exercise exercise = mapper.exerciseDtoToExercise(o);
        exerciseRepository.save(exercise);
        return findById(exercise.getId());
    }

    @Override
    public long count() {
        LOGGER.info("-=count()=-");
        return exerciseRepository.count();
    }

    @Override
    public Page<ExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest) {
        LOGGER.info("-=Page<ExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest)=-");
        Specification<Exercise> spec = ExerciseSpecification.trueLiteral();
        if (!params.isEmpty() && params.containsKey("name_filter") && !params.get("name_filter").isBlank()) {
            spec = spec.and(ExerciseSpecification.nameContains(params.get("name_filter")));
        }
        return exerciseRepository.findAll(spec, pageRequest).map(obj -> modelMapper.map(obj, ExerciseDto.class));
    }
}
