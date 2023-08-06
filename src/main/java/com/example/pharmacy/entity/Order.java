package com.example.pharmacy.entity;

import com.example.pharmacy.util.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@DynamicInsert
public class Order extends BaseEntity {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMedicine> orderMedicines = new ArrayList<>();

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @CreatedDate
    @ReadOnlyProperty
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addOrderMedicine (OrderMedicine orderMedicine){
        orderMedicines.add(orderMedicine);
        orderMedicine.setOrder(this);
    }

    public void removeOrderMedicine (OrderMedicine orderMedicine){
        orderMedicines.remove(orderMedicine);
        orderMedicine.setOrder(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderMedicines=").append(orderMedicines);
        sb.append(", deliveryAddress='").append(deliveryAddress).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
