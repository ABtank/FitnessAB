package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Integer id;
    @NonNull
    private String name;
    private String descr;
    private String creatorLogin;
    private Date createDate;
}
