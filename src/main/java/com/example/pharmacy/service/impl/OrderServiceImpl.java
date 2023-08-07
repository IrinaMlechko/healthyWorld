package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.service.OrderService;
import com.example.pharmacy.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findByUserId(int id) {
        log.info("Find order by user id: " + id);
        Optional<Order> orderOptional = Optional.empty();
        List<Order> orders = orderRepository.findAllByUserIdAndStatusOrderByCreatedAt(id, Status.NEW);
        if(!orders.isEmpty()){
            orderOptional = Optional.ofNullable(orders.get(0));
        }
        return orderOptional;
    }

    @Override
    public Order updateOrderStatus(Order order, Status status) {
            log.info(String.format("Updating status to %s for order with id: ", status, order.getId());
            order.setStatus(status);
            return orderRepository.save(order);
    }

    @Override
    public Order findById(int orderId) {
        log.info("Find order by id: " + orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
    }


}
