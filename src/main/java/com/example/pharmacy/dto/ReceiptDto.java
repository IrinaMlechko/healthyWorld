package com.example.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
    private int id;
    private String medicineName;
    private String patientFirstName;
    private String patientLastName;
    private LocalDate patientsDateOfBirth;
    private int quantity;
}
