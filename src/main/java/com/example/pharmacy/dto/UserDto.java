package com.example.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
}
