package ru.abtank.fitnessab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String login;
    private String email;
    private List<String> roles;
    private Date createDate;
    private Date modifyDate;

    public UserDto(Integer id, String login, String email, List<String> roles, Date createDate, Date modifyDate) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.roles = roles;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
