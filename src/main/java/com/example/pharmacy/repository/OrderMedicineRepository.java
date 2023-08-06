package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.OrderMedicine;
import com.example.pharmacy.entity.OrderMedicineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMedicineRepository extends JpaRepository<OrderMedicine, OrderMedicineId> {
}
