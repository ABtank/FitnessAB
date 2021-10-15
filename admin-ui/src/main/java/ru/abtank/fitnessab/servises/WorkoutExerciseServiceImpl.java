package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;
import ru.abtank.fitnessab.persist.repositories.WorkoutExerciseRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutExerciseServiceImpl.class);
    private Mapper mapper;
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setWorkoutExerciseRepository(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    @Override
    public List<WorkoutExerciseDto> findAll() {
        return workoutExerciseRepository.findAll().stream().map(mapper::workoutExerciseToDto).collect(toList());
    }

    @Override
    public Optional<WorkoutExerciseDto> findById(Integer id) {
        return workoutExerciseRepository.findById(id).map(mapper::workoutExerciseToDto);
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
    public WorkoutExerciseDto save(WorkoutExerciseDto o) {
        WorkoutExercise we = workoutExerciseRepository.save(mapper.workoutExerciseDtoToWorkoutExercise(o));
        return mapper.workoutExerciseToDto(we);
    }

    @Override
    public long count() {
        return workoutExerciseRepository.count();
    }
}
