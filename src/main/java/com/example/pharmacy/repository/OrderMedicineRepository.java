package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.OrderMedicine;
import com.example.pharmacy.entity.OrderMedicineId;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMedicineRepository extends JpaRepository<OrderMedicine, OrderMedicineId> {
    List<OrderMedicine> findAllByOrder_UserId(int userId);

    List<OrderMedicine> findAllByOrderUserAndMedicineIdAndOrderStatus(User user, int medicineId, Status status);

    OrderMedicine findByOrder_User_IdAndMedicine_Id(int userId, int medicineId);

    List<OrderMedicine> findAllByOrder(Order order);
}
