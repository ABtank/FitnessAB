package ru.abtank.fitnessab.servises.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;
import ru.abtank.fitnessab.persist.repositories.WorkoutExerciseRepository;
import ru.abtank.fitnessab.servises.WorkoutExerciseService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseServiceImpl.class);
    private WorkoutExerciseRepository workoutExerciseRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWorkoutExerciseRepository(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
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
    public long count() {
        return workoutExerciseRepository.count();
    }
}
