package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.Order;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1);
        testOrder.setStatus(Status.NEW);
    }

    @Test
    void testFindByUserId_NoOrder() {
        int userId = 1;
        when(orderRepository.findAllByUserIdAndStatusOrderByCreatedAt(userId, Status.NEW)).thenReturn(new ArrayList<>());

        Optional<Order> result = orderService.findByUserId(userId);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void testFindByUserId_WithOrder() {
        int userId = 1;
        List<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        when(orderRepository.findAllByUserIdAndStatusOrderByCreatedAt(userId, Status.NEW)).thenReturn(orders);

        Optional<Order> result = orderService.findByUserId(userId);

        assertEquals(testOrder, result.get());
    }

    @Test
    void testUpdateOrderStatus() {
        Status newStatus = Status.ACCOMPLISHED;
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        Order result = orderService.updateOrderStatus(testOrder, newStatus);

        assertEquals(newStatus, result.getStatus());
        verify(orderRepository, times(1)).save(testOrder);
    }
}
