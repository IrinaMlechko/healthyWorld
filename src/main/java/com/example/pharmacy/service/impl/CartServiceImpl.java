package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.dto.OrderMedicineDto;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.OrderMedicine;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.NoActiveOrderFoundException;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.CartService;
import com.example.pharmacy.service.OrderService;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.ReceiptStatus;
import com.example.pharmacy.util.Status;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final OrderMedicineRepository orderMedicineRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ReceiptService receiptService;

    public CartServiceImpl(UserRepository userRepository, OrderMedicineRepository orderMedicineRepository, OrderRepository orderRepository, OrderService orderService, ReceiptService receiptService) {
        this.userRepository = userRepository;
        this.orderMedicineRepository = orderMedicineRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.receiptService = receiptService;
    }
    @Override
    public OrderDto getCartContents(int userId) {
        Optional<Order> order = orderService.findByUserId(userId);
        List<OrderMedicineDto> orderMedicineDtos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        OrderDto orderDto = new OrderDto(orderMedicineDtos, totalPrice, false);
        if (order.isEmpty()){
            return orderDto;
        }
        int orderId = order.get().getId();

        boolean isReadyToBuy = true;

        List<OrderMedicine> orderMedicines = orderMedicineRepository.findAllByOrder_Id(orderId);
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
        orderDto.setOrderMedicines(orderMedicineDtos);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setReadyToBuy(isReadyToBuy);
        return orderDto;
    }

    @Override
    public boolean isReadyToBuy(int orderId){
        ReceiptStatus[] statusesToCheck = {ReceiptStatus.NO_RECEIPT_NEEDED, ReceiptStatus.RECEIPT_PROVIDED};
        List<OrderMedicine> orderMedicines = orderMedicineRepository.findAllByOrder_Id(orderId);
        for (OrderMedicine orderMedicine : orderMedicines) {
            if (!ArrayUtils.contains(statusesToCheck, orderMedicine.getReceiptStatus())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeItemFromCart(int userId, int medicineId) {
        OrderMedicine orderMedicine = orderMedicineRepository.findByOrder_User_IdAndMedicine_IdAndOrder_Status(userId, medicineId, Status.NEW);
        if (orderMedicine != null) {
            Order order = orderMedicine.getOrder();
            orderMedicineRepository.delete(orderMedicine);
            List<OrderMedicine> remainingOrderMedicines = orderMedicineRepository.findAllByOrder(order);
            if (remainingOrderMedicines.isEmpty()) {
                orderRepository.delete(order);
            }
        }
    }

    @Override
    public void updateItemQuantity(int userId, int medicineId, int quantity) {
        OrderMedicine orderMedicine = orderMedicineRepository.findByOrder_User_IdAndMedicine_IdAndOrder_Status(userId, medicineId, Status.NEW);
        if (orderMedicine != null) {
            if (quantity <= 0) {
                removeItemFromCart(userId, medicineId);
            } else {
                orderMedicine.setQuantity(quantity);
                orderMedicineRepository.save(orderMedicine);
            }
        }
    }
    @Override
    public int getOrderId(int userId) throws NoActiveOrderFoundException {
        Optional<Order> orderOptional = orderRepository.findByUser_IdAndStatus(userId, Status.NEW);
        if (orderOptional.isEmpty()) {
            throw new NoActiveOrderFoundException("Error occurred: no active order found for user with id:" + userId);
        }
        return orderOptional.get().getId();
    }
    @Override
    public void completePurchase(int orderId) {
        Order order = orderService.findById(orderId);
        int userId = order.getUser().getId();

        OrderDto orderDto= getCartContents(userId);
        orderService.updateOrderStatus(order, Status.ACCOMPLISHED);

        List<OrderMedicineDto> orderMedicines = orderDto.getOrderMedicines();

        for (OrderMedicineDto orderMedicine : orderMedicines) {
            if (orderMedicine.getReceiptStatus() == ReceiptStatus.RECEIPT_PROVIDED) {
                int medicineId = orderMedicine.getMedicineId();
                int quantityToPurchase = orderMedicine.getQuantity();

                List<Receipt> receipts = receiptService.findByPatientIdAndMedicineId(userId, medicineId);

                for (Receipt receipt : receipts) {
                    int receiptQuantity = receipt.getQuantity();
                    if (quantityToPurchase >= receiptQuantity) {
                        quantityToPurchase -= receiptQuantity;
                        receiptService.deleteReceiptById(receipt.getId());
                    } else {
                        receiptQuantity -= quantityToPurchase;
                        receiptService.updateReceiptQuantity(receipt, receiptQuantity);
                        break;
                    }
                }
            }
        }
    }
}
