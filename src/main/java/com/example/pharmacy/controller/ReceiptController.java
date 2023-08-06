package com.example.pharmacy.controller;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) throws ServiceException {
        this.receiptService = receiptService;
    }

    @GetMapping(path = "/tasks")
    public String findAllOpenedReceipts(Model model, HttpSession session) throws ServiceException {
        List<ReceiptDto> receipts = receiptService.findAllOpenedReceipts();
        model.addAttribute("receipts", receipts);
        return "receipts";
    }

    @PostMapping("/confirm/{receiptId}")
    public String confirmReceipt(@PathVariable int receiptId, HttpSession session) throws ServiceException {
        if (session.getAttribute("userId") != null && session.getAttribute("role").equals(Role.DOCTOR)) {
            int doctorId = (int) session.getAttribute("userId");
            receiptService.confirmReceipt(receiptId, doctorId);
            return "redirect:/tasks";
        } else {
            return "impossible_to_confirm_receipt";
        }
    }

}
