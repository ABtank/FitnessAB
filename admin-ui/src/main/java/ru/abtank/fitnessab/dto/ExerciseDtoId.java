package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExerciseDtoId {
    private Integer id;

    public ExerciseDtoId(Integer id) {
        this.id = id;
    }
}
