package com.example.pharmacy.controller;

import com.example.pharmacy.dto.OrderDto;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.NoActiveOrderFoundException;
import com.example.pharmacy.service.CartService;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.ReceiptService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ReceiptService receiptService;
    private final MedicineService medicineService;

    public CartController(CartService cartService, ReceiptService receiptService, MedicineService medicineService) {
        this.cartService = cartService;
        this.receiptService = receiptService;
        this.medicineService = medicineService;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        OrderDto orderDto = cartService.getCartContents(userId);
        model.addAttribute("orderDto", orderDto);
        return "cart";
    }


    @PostMapping("/buy")
    public String buyItems(Model model, HttpSession session) throws NoActiveOrderFoundException {
        int userId = (int) session.getAttribute("userId");
        int orderId = cartService.getOrderId(userId);
        boolean isReadyToBuy = cartService.isReadyToBuy(userId);
        if (isReadyToBuy) {
            cartService.completePurchase(orderId);
            session.removeAttribute("orderId");
            return "buy_success";
        } else {
            String errorMsg = "Receipts required.";
            model.addAttribute("errorMsg", errorMsg);
            return "cart";
        }
    }

    @DeleteMapping("/remove/{medicineId}")
    public String removeItemFromCart(@PathVariable int medicineId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        cartService.removeItemFromCart(userId, medicineId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{medicineId}")
    public String updateItemQuantity(@PathVariable int medicineId, @RequestParam int quantity, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        cartService.updateItemQuantity(userId, medicineId, quantity);
        List<Receipt> receipts = receiptService.findByPatientIdAndMedicineId(userId, medicineId);
        if (!receipts.isEmpty()) {
            for (Receipt receipt : receipts) {
                medicineService.refreshReceiptsStatus(receipt.getId());
            }
        }
        return "redirect:/cart";
    }
}

