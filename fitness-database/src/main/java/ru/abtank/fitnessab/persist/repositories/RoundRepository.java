package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Round;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer>, JpaSpecificationExecutor<Round> {
    List<Round> findByWorkoutExerciseOrderBySessionDate(WorkoutExercise workoutExerciseId);
    Optional<Round> findFirstByWorkoutExerciseOrderBySessionDateDesc(WorkoutExercise workoutExerciseId);
}