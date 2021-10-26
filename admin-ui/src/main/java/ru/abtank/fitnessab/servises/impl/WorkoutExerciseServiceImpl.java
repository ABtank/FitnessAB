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
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.persist.entities.Exercise;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;
import ru.abtank.fitnessab.persist.repositories.WorkoutExerciseRepository;
import ru.abtank.fitnessab.persist.repositories.WorkoutRepository;
import ru.abtank.fitnessab.persist.repositories.specifications.ExerciseSpecification;
import ru.abtank.fitnessab.persist.repositories.specifications.WorkoutExerciseSpecification;
import ru.abtank.fitnessab.servises.WorkoutExerciseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseServiceImpl.class);
    private WorkoutExerciseRepository workoutExerciseRepository;
    private WorkoutRepository workoutRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWorkoutExerciseRepository(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    @Autowired
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<WorkoutExerciseDto> findAll() {
        return workoutExerciseRepository.findAll().stream().map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class)).collect(toList());
    }

    @Override
    public Optional<WorkoutExerciseDto> findById(Integer id) {
        return workoutExerciseRepository.findById(id).map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class));
    }

    @Override
    @Deprecated
    public Optional<WorkoutExerciseDto> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        workoutExerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all workoutExercise");
    }

    @Override
    public Optional<WorkoutExerciseDto> save(WorkoutExerciseDto o) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.save(modelMapper.map(o, WorkoutExercise.class));
        return findById(workoutExercise.getId());
    }

    @Override
    public Page<WorkoutExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest) {
        LOGGER.info("-=Page<WorkoutExerciseDto> findAll(Map<String, String> params, PageRequest pageRequest)=-");
        Specification<WorkoutExercise> spec = WorkoutExerciseSpecification.trueLiteral();
        if (!params.isEmpty() && params.containsKey("workoutId") && !params.get("workoutId").isBlank()) {
            LOGGER.info("spec="+spec);
            Workout workout = workoutRepository.findById(Integer.valueOf(params.get("workoutId"))).orElseThrow(NotFoundException::new);
            spec = spec.and(WorkoutExerciseSpecification.workoutEquals(workout));
        }
        LOGGER.info("spec="+spec);
        List<WorkoutExercise> all = workoutExerciseRepository.findAll(spec);
        System.out.println("all.isEmpty()");
        System.out.println(all.isEmpty());
        LOGGER.info("all is empty="+all.isEmpty());
        LOGGER.info("all first="+all.stream().findFirst().map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class)));
        return workoutExerciseRepository.findAll(spec, pageRequest).map(obj -> modelMapper.map(obj, WorkoutExerciseDto.class));
    }

    @Override
    public long count() {
        return workoutExerciseRepository.count();
    }
}
