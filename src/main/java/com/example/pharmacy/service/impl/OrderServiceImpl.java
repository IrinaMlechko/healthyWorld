package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.mapper.ReceiptMapper;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.repository.ReceiptRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.OrderService;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findByUserID(int id) {
        log.info("Find user by id: " + id);
        Optional<Order> orderOptional = Optional.empty();
        List<Order> orders = orderRepository.findAllByUserIdAndStatusOrderByCreatedAt(id, Status.NEW);
        if(!orders.isEmpty()){
            orderOptional = Optional.ofNullable(orders.get(0));
        }
        return orderOptional;
    }



}
