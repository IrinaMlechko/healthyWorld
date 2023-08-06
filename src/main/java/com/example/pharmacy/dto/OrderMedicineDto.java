package com.example.pharmacy.dto;

import com.example.pharmacy.util.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMedicineDto {
    private String medicineName;
    private String manufacturer;
    private BigDecimal price;
    private Integer quantity;
    private ReceiptStatus receiptStatus;
}
