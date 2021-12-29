package com.github.alllef.servlet.auth.login;

import com.github.alllef.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logged")
public class MyLoggedServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ggg");
        HttpSession httpSession = req.getSession(false);
        User user = (User) httpSession.getAttribute("user");
        String page = switch (user.getUserType()) {
            case CLIENT -> "/main/user.jsp";
            case MANAGER -> "/main/manager.jsp";
            case ADMINISTRATOR -> "/main/admin.jsp";
        };

        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
