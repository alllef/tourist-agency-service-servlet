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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/my-account")
public class MyAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        var tourRequests = client.getRequestsWithToursByUser(user);

        PrintWriter out = resp.getWriter();
        for (TourRequest tourRequest : tourRequests.keySet())
            out.println(formTemplate(tourRequest, tourRequests.get(tourRequest)));

    }

    private String formTemplate(TourRequest tourRequest, Tour tour) {
        int finalPrice = ((100 - tourRequest.getDiscount()) * tour.getPrice()) / 100;
        return String.format(tourRequestTemplate, tour.getTourType().toString(), tour.getPrice(), tour.getHotelType().toString(),
                tour.getPeopleNumber(), tourRequest.getRequestStatus(), tourRequest.getDiscount(), finalPrice);
    }
}

