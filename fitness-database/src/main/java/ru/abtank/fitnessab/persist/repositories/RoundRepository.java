package ru.abtank.fitnessab.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.fitnessab.persist.entities.Round;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer>, JpaSpecificationExecutor<Round> {
    List<Round> findByWorkoutExerciseOrderBySessionDate(Integer workout_exercise_id);
    Optional<Round> findFirstByWorkoutExerciseOrderBySessionDateDesc(Integer workout_exercise_id);
}