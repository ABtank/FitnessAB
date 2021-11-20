package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatorDto {
    private Integer id;
    private String login;
    private String email;

    public CreatorDto(UserDto user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getLogin();
    }
}
