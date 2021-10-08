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
    private String descr;

    public ModeDto(Mode mode) {
        this.id = mode.getId();
        this.name = mode.getName();
        this.isStart = mode.getIsStart();
    }

    public ModeDto(Integer id, String name, Boolean isStart, String descr) {
        this.id = id;
        this.name = name;
        this.isStart = isStart;
        this.descr = descr;
    }
}
