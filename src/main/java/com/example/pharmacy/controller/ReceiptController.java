package com.example.pharmacy.controller;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.example.pharmacy.constant.AttributeName.RECEIPTS;
import static com.example.pharmacy.constant.AttributeName.USER_ID;
import static com.example.pharmacy.constant.PageName.IMPOSSIBLE_TO_CONFIRM_PAGE;
import static com.example.pharmacy.constant.PageName.RECEIPTS_PAGE;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final MedicineService medicineService;

    public ReceiptController(ReceiptService receiptService, MedicineService medicineService) {
        this.receiptService = receiptService;
        this.medicineService = medicineService;
    }

    @GetMapping(path = "/tasks")
    public String findAllOpenedReceipts(Model model, HttpSession session) throws ServiceException {
        List<ReceiptDto> receipts = receiptService.findAllOpenedReceipts();
        model.addAttribute(RECEIPTS, receipts);
        return RECEIPTS_PAGE;
    }

    @PostMapping("/confirm/{receiptId}")
    public String confirmReceipt(@PathVariable int receiptId, HttpSession session) throws ServiceException {
        if (session.getAttribute("userId") != null && session.getAttribute("role").equals(Role.DOCTOR)) {
            int doctorId = (int) session.getAttribute(USER_ID);
            receiptService.confirmReceipt(receiptId, doctorId);
            medicineService.refreshReceiptsStatus(receiptId);
            return "redirect:/tasks";
        } else {
            return IMPOSSIBLE_TO_CONFIRM_PAGE;
        }
    }

}
