package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.OrderService;
import com.example.pharmacy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import static com.example.pharmacy.constant.AttributeName.*;
import static com.example.pharmacy.constant.PageName.CATALOG_PAGE;

@Controller
public class MedicineController {

    private final MedicineService medicineService;
    private final UserService userService;
    private final OrderService orderService;

    public MedicineController(MedicineService medicineService, UserService userService, OrderService orderService){
        this.medicineService = medicineService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/catalog")
    public String findAllMedicines(Model model, HttpSession session) throws ServiceException {
        List<Medicine> medicines = medicineService.findAll();
        model.addAttribute(MEDICINES, medicines);
        return CATALOG_PAGE;
    }

    @GetMapping("/addToCart/{medicineId}")
    public String buyMedicinePage(@PathVariable int medicineId, Model model) {
        model.addAttribute(MEDICINE_ID, medicineId);
        return "add_medicine_to_cart";
    }

    @PostMapping("/addToCart/{medicineId}")
    public String addMedicineToCart(@PathVariable int medicineId, HttpSession session) {
        Integer orderId = (Integer) session.getAttribute(ORDER_ID);
        User user = null;
        int userId = (int) session.getAttribute(USER_ID);
        if (orderId == null) {
            Optional<User> userOptional = userService.findUserById(userId);
            if (userOptional.isEmpty()) {
                return "redirect:/";
            } else {
                user = userOptional.get();
            }
            Optional<Order> orderOptional = orderService.findByUserId(userId);
            if (orderOptional.isEmpty()) {
                orderId = medicineService.createOrder(user);
            } else {
                orderId = orderOptional.get().getId();
            }
            session.setAttribute(ORDER_ID, orderId);
        }
        medicineService.addMedicineToOrder(orderId, medicineId, 1, userId);
        return "redirect:/catalog";
    }
}
