package com.example.pharmacy.mapper;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Receipt;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptDto toDto(Receipt receipt) {
        if (receipt == null) {
            return null;
        }

        ReceiptDto dto = new ReceiptDto();
        dto.setId(receipt.getId());
        dto.setMedicineName(receipt.getMedicine().getMedicineName());
        dto.setPatientFirstName(receipt.getPatient().getFirstName());
        dto.setPatientLastName(receipt.getPatient().getLastName());
        dto.setPatientsDateOfBirth(receipt.getPatient().getDateOfBirth());
        dto.setQuantity(receipt.getQuantity());
        return dto;
    }
}
