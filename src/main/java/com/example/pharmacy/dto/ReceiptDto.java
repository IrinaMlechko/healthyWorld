package com.example.pharmacy.dto;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
