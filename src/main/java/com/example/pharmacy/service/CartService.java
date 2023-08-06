package com.example.pharmacy.service;

import com.example.pharmacy.dto.OrderDto;

public interface CartService {
    OrderDto getCartContents(int userId);
    boolean isReadyToBuy();

    void removeItemFromCart(int userId, int medicineId);
}
