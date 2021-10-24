package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Integer id;
    private String name;
    private String descr;
    private CreatorDto creator;
    private Date createDate;
}
