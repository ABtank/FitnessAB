package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ModeDto {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Boolean isStart;
    private String descr;
}
