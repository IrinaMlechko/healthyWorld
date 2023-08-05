package com.example.pharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMedicineId implements Serializable {
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "medicine_id")
    private int medicineId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderMedicineId{");
        sb.append("orderId=").append(orderId);
        sb.append(", medicineId=").append(medicineId);
        sb.append('}');
        return sb.toString();
    }
}