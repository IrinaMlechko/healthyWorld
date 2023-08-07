package com.example.pharmacy.service;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.util.Status;

import java.util.Optional;

public interface OrderService {
    Optional<Order> findByUserId(int id);

    Order updateOrderStatus(Order order, Status status);

    Order findById(int orderId);
}
