package ru.abtank.fitnessab.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
public class RoundDto {
    private Integer id;
    private Integer workoutExerciseId;
    private String exercise;
    private String weight;
    private Integer reps;
    private String descr;
    private String cardio1;
    private String cardio2;
    private String cardio3;
    private Date sessionDate;
    private Date createDate;

    public RoundDto(Integer id, Integer workoutExerciseId, String exercise, String weight, Integer reps, String descr, String cardio1, String cardio2, String cardio3, Date sessionDate, Date createDate) {
        this.id = id;
        this.workoutExerciseId = workoutExerciseId;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.descr = descr;
        this.cardio1 = cardio1;
        this.cardio2 = cardio2;
        this.cardio3 = cardio3;
        this.sessionDate = sessionDate;
        this.createDate = createDate;
    }
}
