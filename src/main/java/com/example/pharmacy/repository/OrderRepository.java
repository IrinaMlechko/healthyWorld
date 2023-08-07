package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserIdAndStatusOrderByCreatedAt(int userId, Status status);

    Optional<Order> findByUser_Id(int userId);

    Optional<Order> findByUser_IdAndStatus(int userId, Status status);
}
