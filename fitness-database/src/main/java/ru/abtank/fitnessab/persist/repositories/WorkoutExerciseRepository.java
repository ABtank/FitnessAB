package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Workout;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer>, JpaSpecificationExecutor<WorkoutExercise> {

    List<WorkoutExercise> findByWorkoutEquals (Workout workout);

}