package com.example.pharmacy.service.impl;


import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.entity.*;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.OrderService;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.ReceiptStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMedicineRepository orderMedicineRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private CartServiceImpl cartService;

    private User testUser;
    private Medicine testMedicine;
    private Order testOrder;
    private List<OrderMedicine> testOrderMedicines;

    @BeforeEach
    void setUp() {

        testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("Name");
        testUser.setLastName("Lname");

        testMedicine = new Medicine();
        testMedicine.setId(1);
        testMedicine.setMedicineName("Test Medicine");
        testMedicine.setManufacturer("Test Manufacturer");
        testMedicine.setPrice(BigDecimal.valueOf(10.0));

        testOrder = new Order();
        testOrder.setId(1);
        testOrder.setUser(testUser);

        testOrderMedicines = new ArrayList<>();
        OrderMedicine testOrderMedicine = new OrderMedicine();
        testOrderMedicine.setId(new OrderMedicineId(testOrder.getId(), testMedicine.getId()));
        testOrderMedicine.setOrder(testOrder);
        testOrderMedicine.setMedicine(testMedicine);
        testOrderMedicine.setQuantity(2);
        testOrderMedicine.setReceiptStatus(ReceiptStatus.RECEIPT_PROVIDED);
        testOrderMedicines.add(testOrderMedicine);
    }

    @Test
    void testGetCartContents() {
        when(orderService.findByUserId(1)).thenReturn(Optional.of(testOrder));
        when(orderMedicineRepository.findAllByOrder_Id(1)).thenReturn(testOrderMedicines);

        OrderDto orderDto = cartService.getCartContents(1);

        assertNotNull(orderDto);
        assertEquals(testOrderMedicines.size(), orderDto.getOrderMedicines().size());
        assertEquals(testOrderMedicines.get(0).getMedicine().getMedicineName(),
                orderDto.getOrderMedicines().get(0).getMedicineName());
        assertEquals(testOrderMedicines.get(0).getMedicine().getPrice(),
                orderDto.getOrderMedicines().get(0).getPrice());
        assertEquals(testOrderMedicines.get(0).getQuantity(),
                orderDto.getOrderMedicines().get(0).getQuantity());
        assertEquals(testOrderMedicines.get(0).getReceiptStatus(),
                orderDto.getOrderMedicines().get(0).getReceiptStatus());
    }

    @Test
    void testGetCartContentsNoOrder() {
        when(orderService.findByUserId(1)).thenReturn(Optional.empty());

        OrderDto orderDto = cartService.getCartContents(1);

        assertNotNull(orderDto);
        assertTrue(orderDto.getOrderMedicines().isEmpty());
    }

    @Test
    void testIsReadyToBuy() {
        int orderId = 1;
        when(orderMedicineRepository.findAllByOrder_Id(orderId)).thenReturn(testOrderMedicines);

        boolean result = cartService.isReadyToBuy(orderId);

        assertTrue(result);
    }
}
