package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RoundDto {
    private Integer id;
    private WorkoutDtoId workout;
    private ExerciseDtoId exercise;
    private String weight;
    private Integer reps;
    private String descr;
    private String cardio1;
    private String cardio2;
    private String cardio3;
    private Date sessionDate;

    public RoundDto(Integer id, WorkoutDtoId workout, ExerciseDtoId exercise, String weight, Integer reps, String descr, String cardio1, String cardio2, String cardio3) {
        this.id = id;
        this.workout = workout;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.descr = descr;
        this.cardio1 = cardio1;
        this.cardio2 = cardio2;
        this.cardio3 = cardio3;
    }
}
