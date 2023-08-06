package com.example.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<OrderMedicineDto> orderMedicines;
    private BigDecimal totalPrice;
    private boolean isReadyToBuy;
}
