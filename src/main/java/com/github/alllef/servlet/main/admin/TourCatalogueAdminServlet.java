package com.github.alllef.servlet.main.admin;

import com.github.alllef.servlet.command.AdminTourCatalogueCommand;
import com.github.alllef.servlet.command.CommandList;
import com.github.alllef.servlet.command.ServletCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/admin/tour-catalogue")
public class TourCatalogueAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletCommand command = CommandList.findCommand(req.getServletPath()).getServletCommand();
        Optional<String> forwardingPage = command.execute(req,resp);
        if (forwardingPage.isPresent())
            req.getRequestDispatcher(forwardingPage.get()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletCommand command = CommandList.findCommand(req.getServletPath()).getServletCommand();
        Optional<String> forwardingPage = command.execute(req,resp);
        if (forwardingPage.isPresent())
            req.getRequestDispatcher(forwardingPage.get()).forward(req, resp);

    }

}
