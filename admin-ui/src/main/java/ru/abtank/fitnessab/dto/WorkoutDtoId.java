package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutDtoId {
    private Integer id;

    public WorkoutDtoId(Integer id) {
        this.id = id;
    }
}
