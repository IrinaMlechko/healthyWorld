package com.example.pharmacy.controller;

import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.util.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        try {
            Optional<User> userOptional = userService.authenticate(login, password);
            if (userOptional.isPresent()){
                String firstName = userOptional.get().getFirstName();
                Role role = userOptional.get().getCredentials().getRole();
                session.setAttribute("userName", firstName);
                session.setAttribute("role", role);
                return "redirect:/catalog";
            } else {
                model.addAttribute("loginFailed", true);
                return "redirect:/index";
            }
        } catch (ServiceException e) {
            model.addAttribute("errorMsg", e.getLocalizedMessage());
            return "redirect:/error_500";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@RequestParam("login") String login,
                           @RequestParam("password") String password,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("dateOfBirth") String dateOfBirthStr,
                           Model model) {
        // Handle user registration logic here
        return "redirect:/";
    }
}
