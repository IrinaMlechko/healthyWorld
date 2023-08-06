package com.example.pharmacy.controller;

import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        OrderDto orderDto = cartService.getCartContents(userId);
        model.addAttribute("orderDto", orderDto);
        return "cart";
    }

    @PostMapping("/buy")
    public String buyItems(Model model) {
        boolean isReadyToBuy = cartService.isReadyToBuy();
        if (isReadyToBuy) {
            // Perform the buy operation here
            // ...
            return "buy_success"; // Return the success view after buy
        } else {
            String errorMsg = "Receipts required.";
            model.addAttribute("errorMsg", errorMsg);
            return "cart";
        }
    }
}

