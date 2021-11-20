package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CounterDto {
    private Integer workout;
    private Integer exercise;
    private Integer category;
    private Integer round;
    private Integer mode;
    private Integer type;
    private Integer role;
    private Integer user;
}
