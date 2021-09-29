package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.fitnessab.persist.entities.Mode;

@Data
@NoArgsConstructor
public class ModeDto {
    private Integer id;
    private String name;
    private Boolean isStart;

    public ModeDto(Mode mode) {
        this.id = mode.getId();
        this.name = mode.getName();
        this.isStart = mode.getIsStart();
    }
}
