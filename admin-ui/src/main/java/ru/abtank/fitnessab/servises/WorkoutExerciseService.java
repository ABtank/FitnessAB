package ru.abtank.fitnessab.servises;

import ru.abtank.fitnessab.dto.WorkoutExerciseDto;
import ru.abtank.fitnessab.persist.entities.WorkoutExerciseId;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseService {
    List<WorkoutExerciseDto> findAll();
    Optional<WorkoutExerciseDto> findById(WorkoutExerciseId id);
    void deleteById(WorkoutExerciseId id);
    void deleteAll();
    WorkoutExerciseDto save(WorkoutExerciseDto o);
}
