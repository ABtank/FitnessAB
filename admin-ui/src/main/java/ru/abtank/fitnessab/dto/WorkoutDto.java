package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Integer id;
    private String name;
    private String descr;
    private CreatorDto creator;

    public WorkoutDto(Integer id, String name, String descr, CreatorDto creator) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.creator = creator;
    }

    public WorkoutDto(Integer id) {
        this.id = id;
    }
}
