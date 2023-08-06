package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.dto.OrderMedicineDto;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.OrderMedicine;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.CartService;
import com.example.pharmacy.util.ReceiptStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final OrderMedicineRepository orderMedicineRepository;
    private final OrderRepository orderRepository;

    public CartServiceImpl(UserRepository userRepository, OrderMedicineRepository orderMedicineRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderMedicineRepository = orderMedicineRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    public OrderDto getCartContents(int userId) {
        List<OrderMedicineDto> orderMedicineDtos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        boolean isReadyToBuy = true;

        List<OrderMedicine> orderMedicines = orderMedicineRepository.findAllByOrder_UserId(userId);
        for (OrderMedicine orderMedicine : orderMedicines) {
            Medicine medicine = orderMedicine.getMedicine();
            OrderMedicineDto orderMedicineDto = new OrderMedicineDto(
                    medicine.getId(),
                    medicine.getMedicineName(),
                    medicine.getManufacturer(),
                    medicine.getPrice(),
                    orderMedicine.getQuantity(),
                    orderMedicine.getReceiptStatus()
            );
            orderMedicineDtos.add(orderMedicineDto);
            totalPrice = totalPrice.add(medicine.getPrice().multiply(BigDecimal.valueOf(orderMedicine.getQuantity())));

            if (orderMedicine.getReceiptStatus() == ReceiptStatus.RECEIPT_NEEDED) {
                isReadyToBuy = false;
            }
        }

        return new OrderDto(orderMedicineDtos, totalPrice, isReadyToBuy);
    }

    @Override
    public boolean isReadyToBuy() {
        ReceiptStatus[] statusesToCheck = {ReceiptStatus.NO_RECEIPT_NEEDED, ReceiptStatus.RECEIPT_PROVIDED};
        int userId = 1; // Replace 1 with the actual user ID from the session

        List<OrderMedicine> orderMedicines = orderMedicineRepository.findAllByOrder_UserId(userId);
        for (OrderMedicine orderMedicine : orderMedicines) {
            if (!ArrayUtils.contains(statusesToCheck, orderMedicine.getReceiptStatus())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeItemFromCart(int userId, int medicineId) {
        OrderMedicine orderMedicine = orderMedicineRepository.findByOrder_User_IdAndMedicine_Id(userId, medicineId);
        if (orderMedicine != null) {
            Order order = orderMedicine.getOrder();
            orderMedicineRepository.delete(orderMedicine);
            List<OrderMedicine> remainingOrderMedicines = orderMedicineRepository.findAllByOrder(order);
            if (remainingOrderMedicines.isEmpty()) {
                orderRepository.delete(order);
            }
        }
    }
}
