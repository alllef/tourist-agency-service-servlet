package com.github.alllef.servlet.main.manager;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.model.service.ManagerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/managing/tours")
public class TourManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ManagerService managerService = new ManagerService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO());
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());

        PrintWriter out = resp.getWriter();
        for (Tour tour : client.filterTours(req.getParameterMap()))
            out.println(formTemplate(tour));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("What the hell");
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        PrintWriter out = resp.getWriter();
        ManagerService managerService = new ManagerService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO());
        long tourId = Long.parseLong(req.getParameter("update-tour"));
        boolean isBurning = Boolean.parseBoolean(req.getParameter("burning"));
        int maxDiscount = Integer.parseInt(req.getParameter("max-discount"));

        try {
            managerService.updateTour(tourId, maxDiscount, isBurning);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Tour tour : client.filterTours(req.getParameterMap()))
            out.println(formTemplate(tour));
    }

    private String formTemplate(Tour tour) {
        String checkedBurning = "";
        if (tour.isBurning())
            checkedBurning = "checked";

        return String.format(managerCatalogueTemplate, tour.getHotelType().toString(), tour.getPrice(),
                tour.getTourType().toString(), tour.getPeopleNumber(), tour.getMaxDiscount(), checkedBurning, tour.getTourId());
    }
}
