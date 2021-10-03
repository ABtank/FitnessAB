package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatorDto {
    private Integer id;
    private String login;
    private String email;


    public CreatorDto(Integer id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }
}
