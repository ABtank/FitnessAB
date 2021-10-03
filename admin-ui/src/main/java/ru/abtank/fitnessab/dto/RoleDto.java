package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.fitnessab.persist.entities.Role;

@Data
@NoArgsConstructor
public class RoleDto {
    private Integer id;
    private String name;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}