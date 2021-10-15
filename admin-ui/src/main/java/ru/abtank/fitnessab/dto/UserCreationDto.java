package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UserCreationDto {
    private Integer id;
    private String login;
    private String password;
    private String matchingPassword;
    private String email;
    private List<String> roles;

    public UserCreationDto(Integer id, String login, String password, String matchingPassword, String email, List<String> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.roles = roles;
    }
}
