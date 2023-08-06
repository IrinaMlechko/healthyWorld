package com.example.pharmacy.service;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findByUserID(int id);

}
