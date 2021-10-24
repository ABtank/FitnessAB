package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class UserCreationDto {
    private Integer id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String matchingPassword;
    @NonNull
    private String email;
    @NonNull
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
