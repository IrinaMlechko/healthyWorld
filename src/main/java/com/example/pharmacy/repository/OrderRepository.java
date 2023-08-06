package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
