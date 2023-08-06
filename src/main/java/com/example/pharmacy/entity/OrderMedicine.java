package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "order_medicines")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
@DynamicUpdate
public class OrderMedicine {

    @EmbeddedId
    private OrderMedicineId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicineId")
    private Medicine medicine;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "receipt_status")
    private String receiptStatus;

    public OrderMedicine (Order order, Medicine medicine, Integer quantity, String receiptStatus){
        this.id = new OrderMedicineId(order.getId(), medicine.getId());
        this.order = order;
        this.medicine = medicine;
        this.quantity = quantity;
        this.receiptStatus = receiptStatus;
    }
}
