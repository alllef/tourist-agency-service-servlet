package com.github.alllef.servlet.main.client;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.model.service.ManagerService;
import com.github.alllef.servlet.command.ClientTourRequestCommand;
import com.github.alllef.servlet.command.CommandList;
import com.github.alllef.servlet.command.ServletCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;

@WebServlet("/my-account")
public class MyAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Account path"+ req.getServletPath());
        ServletCommand command = CommandList.findCommand(req.getServletPath()).getServletCommand();
        Optional<String> forwardingPage = command.execute(req,resp);
        if (forwardingPage.isPresent())
            req.getRequestDispatcher(forwardingPage.get()).forward(req, resp);
    }
}

