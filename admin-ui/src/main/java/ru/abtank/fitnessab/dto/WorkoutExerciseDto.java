package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.fitnessab.persist.entities.WorkoutExerciseId;

@Data
@NoArgsConstructor
public class WorkoutExerciseDto {
    private ModeDto mode;
    private Integer ordinal;
    private String descr;
    private CreatorDto creator;
    private WorkoutExerciseId id;


    public WorkoutExerciseDto(WorkoutExerciseId id, ModeDto mode, Integer ordinal, String descr,
                              CreatorDto creator) {
        this.id= id;
        this.mode = mode;
        this.ordinal = ordinal;
        this.descr = descr;
        this.creator = creator;
    }

}
