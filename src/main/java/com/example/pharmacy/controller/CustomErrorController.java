package com.example.pharmacy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("statusCode", statusCode);
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
