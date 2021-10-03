package ru.abtank.fitnessab.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class WorkoutExerciseId implements Serializable {
    private static final long SerialVersionUID = -8265327880991001253L;

    @Column(name = "workout_id", nullable = false)
    private Integer workoutId;

    @Column (name = "exercise_id", nullable = false)
    private Integer exerciseId;

    public WorkoutExerciseId(Integer workoutId, Integer exerciseId) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
    }
}
