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
}
