package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medicine extends BaseEntity {
    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "created_at")
    @ReadOnlyProperty
    private LocalDateTime createdAt;

    @Column(name = "prescription_required", nullable = false)
    private boolean prescriptionRequired;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMedicine> orderMedicines = new ArrayList<>();

    public void addOrderMedicine (OrderMedicine orderMedicine){
        orderMedicines.add(orderMedicine);
        orderMedicine.setMedicine(this);
    }

    public void removeOrderMedicine (OrderMedicine orderMedicine){
        orderMedicines.remove(orderMedicine);
        orderMedicine.setMedicine(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medicine{");
        sb.append("medicineName='").append(medicineName).append('\'');
        sb.append(", manufacturer='").append(manufacturer).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", prescriptionRequired=").append(prescriptionRequired);
        sb.append(", orderMedicines=").append(orderMedicines);
        sb.append('}');
        return sb.toString();
    }
}
