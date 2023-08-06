package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.util.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserIdAndStatusOrderByCreatedAt(int userId, Status status);
}
