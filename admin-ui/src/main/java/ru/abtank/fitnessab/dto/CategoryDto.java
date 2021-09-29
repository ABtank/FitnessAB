package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.fitnessab.persist.entities.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
    private String descr;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.descr = category.getDescr();
    }
}
