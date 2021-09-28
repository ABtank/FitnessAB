package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.WorkoutExercise;
import ru.abtank.fitnessab.persist.entities.WorkoutExerciseId;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId>, JpaSpecificationExecutor<WorkoutExercise> {

}