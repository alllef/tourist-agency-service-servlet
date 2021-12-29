package com.github.alllef.servlet.main.client;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.servlet.command.ClientTourCatalogueCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/tours/catalogue")
public class TourCatalogueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new ClientTourCatalogueCommand().execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new ClientTourCatalogueCommand().execute(req, resp);
        if (req.getParameter("order") != null) {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            long tourId = Long.parseLong(req.getParameter("order"));
            DaoFactory daoFactory = DaoFactory.getInstance();
            ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
            client.orderTour(tourId, user.getUserId());
        }

        req.getRequestDispatcher("/main/tour-catalogue.jsp").forward(req, resp);
    }

}