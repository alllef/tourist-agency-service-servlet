package com.github.alllef.servlet.auth.filter;

import com.github.alllef.model.entity.User;
import com.github.alllef.utils.security.SecurityUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "authentication", urlPatterns = "/*")
public class AuthenticationFilter implements DefaultFilter {
    @Override
    public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String loginURI = request.getContextPath() + "/login";
        if (request.getServletPath().equals("/login") || request.getServletPath().contains("/register"))
            filterChain.doFilter(request, response);
        else {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            System.out.println(user + "User");
            if (SecurityUtils.hasPermission(user.getUserType(), (request.getServletPath())))
                filterChain.doFilter(request, response);
            else response.sendRedirect(loginURI);
        }
    }
}
