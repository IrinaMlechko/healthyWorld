package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MedicineController {

    private final MedicineService medicineService ;

    public MedicineController(MedicineService medicineService) throws ServiceException {
        this.medicineService = medicineService;
    }

    @GetMapping(path = "/catalog")
    public String findAllMedicines(Model model, HttpSession session) throws ServiceException {
        List<Medicine> medicines = medicineService.findAll();
        model.addAttribute("medicines", medicines);
        return "catalog";
    }
}
