package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;

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
    private String typeId;
    private CreatorDto creator;
    private CategoryDto category;
    @NonNull
    private String categoryId;

    public ExerciseDto(Integer id, String name, String descr, Boolean isCardio, String cardioName1, String cardioName2, String cardioName3, TypeDto type, CreatorDto creator, CategoryDto category) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.isCardio = isCardio;
        this.cardioName1 = cardioName1;
        this.cardioName2 = cardioName2;
        this.cardioName3 = cardioName3;
        this.type = type;
        this.creator = creator;
        this.category = category;
    }

    public ExerciseDto(Integer id) {
        this.id = id;
    }
}
