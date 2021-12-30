package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.model.service.ManagerService;
import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class ManagerTourCommand extends HtmlCommand {
    private final ManagerService managerService;
    private final ClientService clientService;

   public ManagerTourCommand() {
        super(HtmlTemplate.MANAGER_TOUR_CATALOGUE);
        managerService = ManagerService.getInstance();
        clientService = ClientService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.execute(request, response);
        if (request.getMethod().equals("POST"))
            updateTour(request);

            PrintWriter out = response.getWriter();
        for (Tour tour : clientService.filterTours(request.getParameterMap()))
            out.println(formTemplate(tour));
        return Optional.empty();
    }

    public void updateTour(HttpServletRequest req)  {
        long tourId = Long.parseLong(req.getParameter("update-tour"));
        boolean isBurning = Boolean.parseBoolean(req.getParameter("burning"));
        int maxDiscount = Integer.parseInt(req.getParameter("max-discount"));

        managerService.updateTour(tourId, maxDiscount, isBurning);
    }

    private String formTemplate(Tour tour) {
        String checkedBurning = "";
        if (tour.isBurning())
            checkedBurning = "checked";

        return String.format(getTemplate(), tour.getHotelType().toString(), tour.getPrice(),
                tour.getTourType().toString(), tour.getPeopleNumber(), tour.getMaxDiscount(), checkedBurning, tour.getTourId());
    }
}
