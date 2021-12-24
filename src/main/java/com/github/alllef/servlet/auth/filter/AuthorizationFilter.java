package com.github.alllef.servlet.auth.filter;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "authorization", urlPatterns = "/*")
public class AuthorizationFilter implements DefaultFilter {

    @Override
    public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30);
        String loginURI = request.getContextPath() + "/login";
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ClientService client = new ClientService(new TourDAO(ConnectionSingleton.getConnection()), new TourRequestDAO(ConnectionSingleton.getConnection()), new UserDAO(ConnectionSingleton.getConnection()));

        if (session.getAttribute("user") != null)
            filterChain.doFilter(request, response);
        else {
            Optional<User> userOpt = client.findByEmail(email);
            if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
                session.setAttribute("user", userOpt.get());
                filterChain.doFilter(request, response);
            }
            else if(request.getServletPath().equals("/login"))
                filterChain.doFilter(request, response);
            else
                response.sendRedirect(loginURI);
        }
    }

}
