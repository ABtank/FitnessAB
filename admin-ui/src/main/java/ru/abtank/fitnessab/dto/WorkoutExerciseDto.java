package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutExerciseDto {
    private Integer id;
    private WorkoutDtoId workout;
    private ExerciseDtoId exercise;
    private ModeDto mode;
    private Integer ordinal;
    private String descr;


    public WorkoutExerciseDto(Integer id, ModeDto mode, Integer ordinal, String descr,
                              WorkoutDtoId workout, ExerciseDtoId exercise) {
        this.id = id;
        this.mode = mode;
        this.ordinal = ordinal;
        this.descr = descr;
        this.workout = workout;
        this.exercise = exercise;
    }

}
