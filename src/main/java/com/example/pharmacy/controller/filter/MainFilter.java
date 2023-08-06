package com.example.pharmacy.controller.filter;

import com.example.pharmacy.util.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class MainFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (((requestURI.equals("/catalog")) || (requestURI.equals("/tasks"))) && httpRequest.getSession().getAttribute("userName") == null) {
            httpResponse.sendRedirect("/login");
            return;
        }

        Role role = (Role) httpRequest.getSession().getAttribute("role");

        if ((requestURI.equals("/main") || (requestURI.equals("/tasks")))  && !Objects.equals(role, Role.DOCTOR)) {
            httpResponse.sendRedirect("/catalog");
            return;
        }
        else if (requestURI.equals("/main")) {
            httpResponse.sendRedirect("/tasks");
            return;
        }

        chain.doFilter(request, response);
    }
}