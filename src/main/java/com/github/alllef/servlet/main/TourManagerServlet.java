package com.github.alllef.servlet.main;

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

@WebServlet("/managing")
public class TourManagerServlet extends HttpServlet {
    private static final String managerCatalogueTemplate = """
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>
            <form id="manager-update" action="managing" method="post">
                        <h3>Max discount</h3>
                           <input id="discount" name="max-discount" value="%d" type="number"/>
                       <p><input name="burning" type="radio" value ="true" %s>Burning</p>
                       <button type="submit" name="update-tour" value ="%d" title="update-tour">Update tour</button>
                        </form>
            """;

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
            managerService.updateTour(tourId,maxDiscount,isBurning);
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
