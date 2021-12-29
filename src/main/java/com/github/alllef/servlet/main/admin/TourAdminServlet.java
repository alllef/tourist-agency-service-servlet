package com.github.alllef.servlet.main.admin;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.AdminService;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.TourType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.github.alllef.utils.enums.RequestStatus.*;

@WebServlet("/admin/tour")
public class TourAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminService admin = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        long tourId = Long.parseLong(req.getParameter("change-info"));
        try {
            out.println(formTemplate(admin.getTourById(tourId)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminService admin = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        long tourId = Long.parseLong(req.getParameter("save"));
        TourType tourType = TourType.valueOf(req.getParameter("tour-type"));
        int price = Integer.parseInt(req.getParameter("price"));
        int peopleNumber = Integer.parseInt(req.getParameter("people-number"));
        HotelType hotelType = HotelType.valueOf(req.getParameter("hotel-type"));
        int maxDiscount = Integer.parseInt(req.getParameter("max-discount"));
        boolean isBurning = Boolean.parseBoolean(req.getParameter("burning"));
        Tour tour = Tour.builder()
                .tourId(tourId)
                .tourType(tourType)
                .price(price)
                .peopleNumber(peopleNumber)
                .isBurning(isBurning)
                .maxDiscount(maxDiscount)
                .hotelType(hotelType)
                .build();

        admin.updateTour(tour);
        req.getRequestDispatcher("/admin/tour-catalogue").forward(req, resp);
    }

    private String formTemplate(Tour tour) {
        String restSelect = "";
        String excursionSelect = "";
        String shoppingSelect = "";
        String selected = "selected";

        switch (tour.getTourType()) {
            case REST -> restSelect = selected;
            case EXCURSION -> excursionSelect = selected;
            case SHOPPING -> shoppingSelect = selected;
        }

        String hostelSelect = "";
        String allInclusiveSelect = "";

        switch (tour.getHotelType()) {
            case HOSTEL -> hostelSelect = selected;
            case ALL_INCLUSIVE -> allInclusiveSelect = selected;
        }

        String checkedBurning = "";
        if (tour.isBurning())
            checkedBurning = "checked";

        return String.format(tourFormTemplate, restSelect, excursionSelect, shoppingSelect, tour.getPrice(), tour.getPeopleNumber(), hostelSelect, allInclusiveSelect,
                tour.getMaxDiscount(), checkedBurning, tour.getTourId());
    }
}
