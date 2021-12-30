package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.AdminService;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.HtmlTemplate;
import com.github.alllef.utils.enums.TourType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class AdminTourCommand extends HtmlCommand {
    private final AdminService adminService;

    public AdminTourCommand() {
        super(HtmlTemplate.TOUR_FORM);
        this.adminService = AdminService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException{
        super.execute(request, response);
        if (request.getMethod().equals("POST")) {
            updateTour(request);
            return Optional.of("/admin/tour-catalogue");
        }

        PrintWriter out = response.getWriter();
        long tourId = Long.parseLong(request.getParameter("change-info"));
        try {
            out.println(formTemplate(adminService.getTourById(tourId)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateTour(HttpServletRequest req)  {
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

        adminService.updateTour(tour);
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

        return String.format(getTemplate(), restSelect, excursionSelect, shoppingSelect, tour.getPrice(), tour.getPeopleNumber(), hostelSelect, allInclusiveSelect,
                tour.getMaxDiscount(), checkedBurning, tour.getTourId());
    }
}
