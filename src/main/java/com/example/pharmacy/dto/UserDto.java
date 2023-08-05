package com.example.pharmacy.dto;

import com.example.pharmacy.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
