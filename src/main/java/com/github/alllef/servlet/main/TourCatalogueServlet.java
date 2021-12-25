package com.github.alllef.servlet.main;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
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
    private static final String catalogueTemplate = """
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>
            <form id="order" action="catalogue" method="post">
                       <button type="submit" name="order" value="%d" title="Order">Order</button>
                        </form>
            """;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        htmlWithTours(resp.getWriter(), req.getParameterMap());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        long tourId = Long.parseLong(req.getParameter("order"));
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        ClientService client = new ClientService(new TourDAO(ConnectionSingleton.getConnection()), new TourRequestDAO(ConnectionSingleton.getConnection()), new UserDAO(ConnectionSingleton.getConnection()));
        client.orderTour(tourId, user.getUserId());
        htmlWithTours(resp.getWriter(), req.getParameterMap());
    }

    public void htmlWithTours(PrintWriter writer, Map<String, String[]> params) {
        ClientService client = new ClientService(new TourDAO(ConnectionSingleton.getConnection()), new TourRequestDAO(ConnectionSingleton.getConnection()), new UserDAO(ConnectionSingleton.getConnection()));
        StringBuilder resultsPeople = new StringBuilder();
        for (Tour tour : client.filterTours(params))
            resultsPeople.append(String.format(catalogueTemplate, tour.getTourType(), tour.getPrice(), tour.getHotelType(), tour.getPeopleNumber(), tour.getTourId()));
        writer.println(resultsPeople);
    }
}