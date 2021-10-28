package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class WorkoutExerciseDto {
    private Integer id;
    @NonNull
    private Integer workoutId;
    @NonNull
    private Integer exerciseId;
    private String exerciseName;
    private Integer modeId;
    private String modeName;
    private Integer ordinal;
    private String descr;

}
