package ru.abtank.fitnessab.servises;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abtank.fitnessab.dto.WorkoutDto;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.repositories.WorkoutRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@NoArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkoutServiceImpl.class);
    private Mapper mapper;
    private WorkoutRepository workoutRepository;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(mapper::workoutToDto).collect(toList());
    }

    @Override
    public Optional<WorkoutDto> findById(Integer id) {
        return workoutRepository.findById(id).map(mapper::workoutToDto);
    }

    @Override
    public Optional<WorkoutDto> findByName(String name) {
        return workoutRepository.findByName(name).map(mapper::workoutToDto);
    }

    @Override
    public void deleteById(Integer id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.error("Someone decided to delete all Workouts");
    }

    @Override
    public WorkoutDto save(WorkoutDto o) {
        Workout workout = workoutRepository.save(mapper.workoutDtoToWorkout(o));
        return mapper.workoutToDto(workout);
    }

    @Override
    public long count() {
        return workoutRepository.count();
    }
}
