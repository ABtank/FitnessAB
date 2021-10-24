package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ExerciseDto {
    private Integer id;
    @NonNull
    private String name;
    private String descr;
    @NonNull
    private Boolean isCardio;
    private String cardioName1;
    private String cardioName2;
    private String cardioName3;
    private TypeDto type;
    @NonNull
    private Integer typeId;
    private CreatorDto creator;
    private CategoryDto category;
    @NonNull
    private Integer categoryId;
}
