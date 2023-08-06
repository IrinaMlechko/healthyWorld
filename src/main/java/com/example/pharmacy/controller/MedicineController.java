package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MedicineController {

    private final MedicineService medicineService;
    private final UserService userService;

    public MedicineController(MedicineService medicineService, UserService userService) throws ServiceException {
        this.medicineService = medicineService;
        this.userService = userService;
    }

    @GetMapping(path = "/catalog")
    public String findAllMedicines(Model model, HttpSession session) throws ServiceException {
        List<Medicine> medicines = medicineService.findAll();
        model.addAttribute("medicines", medicines);
        return "catalog";
    }
    @GetMapping("/buy/{medicineId}")
    public String buyMedicinePage(@PathVariable int medicineId, Model model) {
        model.addAttribute("medicineId", medicineId);
        return "buy_medicine";
    }

    @PostMapping("/buy/{medicineId}")
    public String buyMedicine(@PathVariable int medicineId,
                              HttpSession session){
        Integer orderId = (Integer) session.getAttribute("orderId");
        if (orderId == null) {
            String login = (String) session.getAttribute("userName");
            Optional<User> userOptional = userService.findUserByLogin(login);
            User user;
            if(userOptional.isEmpty()){
                return "redirect:/";
            } else{
                user = userOptional.get();
            }
            orderId = medicineService.createOrder(user);
            session.setAttribute("orderId", orderId);
        }

        medicineService.addMedicineToOrder(orderId, medicineId, 1);
        return "redirect:/catalog";
    }
}
