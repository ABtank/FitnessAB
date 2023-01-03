package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RoundDto {
    private Integer id;
    private Integer workoutExerciseId;
    private String weight;
    private Integer reps;
    private String descr;
    private String cardio1;
    private String cardio2;
    private String cardio3;
    private Date sessionDate;
    private Date createDate;
}
