package com.example.pharmacy.service;

import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.exception.NoActiveOrderFoundException;

public interface CartService {
    OrderDto getCartContents(int userId);

    boolean isReadyToBuy(int orderId);

    void removeItemFromCart(int userId, int medicineId);

    void updateItemQuantity(int userId, int medicineId, int quantity);

    int getOrderId(int userId) throws NoActiveOrderFoundException;

    void completePurchase(int orderId);
}
