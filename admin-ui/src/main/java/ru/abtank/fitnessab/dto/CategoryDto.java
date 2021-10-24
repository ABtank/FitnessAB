package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NonNull
    private String name;
    private String descr;
    private CreatorDto creator;
}
