package com.example.pharmacy.controller;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.ReceiptService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService ;

    public ReceiptController(ReceiptService receiptService) throws ServiceException {
        this.receiptService = receiptService;
    }

    @GetMapping(path = "/tasks")
    public String findAllOpenedReceipts(Model model, HttpSession session) throws ServiceException {
        List<ReceiptDto> receipts = receiptService.findAllOpenedReceipts();
        model.addAttribute("receipts", receipts);
        return "receipts";
    }
}
