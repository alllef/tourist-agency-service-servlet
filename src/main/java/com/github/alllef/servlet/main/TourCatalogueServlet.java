package com.github.alllef.servlet.main;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.TourType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/tours/catalogue")
public class TourCatalogueServlet extends HttpServlet {
    private static final String catalogueTemplate = """
            <b>Type: %s</b><br>
            <b>Price: %d</b><br>
            <b>Hotel type: %s</b><br>
            <b>Number of people: %d</b><br>""";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        ClientService client = new ClientService(new TourDAO(ConnectionSingleton.getConnection()), new TourRequestDAO(ConnectionSingleton.getConnection()), new UserDAO(ConnectionSingleton.getConnection()));
        List<Tour> tours = client.filterByType(TourType.REST);
        try (PrintWriter out = resp.getWriter()) {
            for (Tour tour : tours) {
                String templateWithData = String.format(catalogueTemplate, tour.getTourType(), tour.getPrice(), tour.getHotelType(), tour.getPeopleNumber());
                out.println(templateWithData);
            }
        }
        req.getRequestDispatcher("/main/tour-catalogue").forward(req, resp);
    }
}