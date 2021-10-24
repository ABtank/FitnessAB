package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.abtank.fitnessab.persist.entities.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NonNull
    private String name;
    private String descr;
    private CreatorDto creator;

    public CategoryDto(Integer id, String name, String descr, CreatorDto creator) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.creator = creator;
    }
}
