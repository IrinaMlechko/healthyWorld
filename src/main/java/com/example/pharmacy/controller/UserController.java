package com.example.pharmacy.controller;

import com.example.pharmacy.dto.UserDto;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.IncorrectDataOfBirthFormat;
import com.example.pharmacy.exception.NotValidLoginException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.exception.UserWithThisLoginAlreadyExists;
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

import static com.example.pharmacy.constant.AttributeName.*;
import static com.example.pharmacy.constant.PageName.*;

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

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        try {
            Optional<User> userOptional = userService.authenticate(login, password);
            if (userOptional.isPresent()) {
                String firstName = userOptional.get().getFirstName();
                Role role = userOptional.get().getCredentials().getRole();
                int userId = userOptional.get().getId();
                session.setAttribute(USER_NAME, firstName);
                session.setAttribute(ROLE, role);
                session.setAttribute(USER_ID, userId);
                return "redirect:/main";
            } else {
                model.addAttribute(LOGIN_FAILED, true);
                return INDEX_PAGE;
            }
        } catch (ServiceException e) {
            model.addAttribute(ERROR_MSG, e.getLocalizedMessage());
            return ERROR_PAGE;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String login,
                               @RequestParam String password,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String dateOfBirth,
                               Model model) throws ServiceException, IncorrectDataOfBirthFormat {
        try {
            UserDto userDto = new UserDto(login, password, firstName, lastName, dateOfBirth);
            userService.registerUser(userDto);
            return "redirect:/signUpSuccessful";
        } catch (UserWithThisLoginAlreadyExists | IllegalArgumentException |  NotValidLoginException e) {
            model.addAttribute(FAILED, e.getMessage());
            return REGISTRATION_PAGE;
        }
    }

    @GetMapping("/signUpSuccessful")
    public String signUpSuccessful() {
        return REGISTRATION_SUCCESSFUL_PAGE;
    }
}
