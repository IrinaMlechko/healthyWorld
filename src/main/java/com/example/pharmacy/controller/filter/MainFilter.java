package com.example.pharmacy.controller.filter;

import com.example.pharmacy.util.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.example.pharmacy.constant.AttributeName.ROLE;

@Component
public class MainFilter implements Filter {

    private static final String CATALOG_URI = "/catalog";
    private static final String TASKS_URI = "/tasks";
    private static final String LOGIN_URI = "/login";
    private static final String MAIN_URI = "/main";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (((requestURI.equals(CATALOG_URI)) || (requestURI.equals(TASKS_URI))) && httpRequest.getSession().getAttribute("userName") == null) {
            httpResponse.sendRedirect(LOGIN_URI);
            return;
        }

        Role role = (Role) httpRequest.getSession().getAttribute(ROLE);

        if ((requestURI.equals(MAIN_URI) || (requestURI.equals(TASKS_URI)))  && !Objects.equals(role, Role.DOCTOR)) {
            httpResponse.sendRedirect(CATALOG_URI);
            return;
        }
        else if (requestURI.equals(MAIN_URI)) {
            httpResponse.sendRedirect(TASKS_URI);
            return;
        }

        chain.doFilter(request, response);
    }
}