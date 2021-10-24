package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TypeDto {
    private Integer id;
    @NonNull
    private String name;
    private String descr;

    public TypeDto(Integer id, String name, String descr) {
        this.id = id;
        this.name = name;
        this.descr = descr;
    }
}
