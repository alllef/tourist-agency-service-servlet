package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.AdminService;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class AdminTourCatalogueCommand extends HtmlCommand {
    private final ClientService clientService;
    private final AdminService adminService;

    public AdminTourCatalogueCommand() {
        super(HtmlTemplate.ADMIN_TOUR_CATALOGUE);
        this.clientService = ClientService.getInstance();
        this.adminService = AdminService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        super.execute(request, response);
        if (request.getMethod().equals("POST"))
            deleteTour(request.getParameter("delete"));

        PrintWriter out = response.getWriter();
        for (Tour tour : clientService.filterTours(request.getParameterMap()))
            out.println(formTemplate(tour));

        return Optional.empty();
    }

    private void deleteTour(String deleteId) {
        if (deleteId != null) {
            adminService.deleteTour(Long.parseLong(deleteId));
        }
    }

    public String formTemplate(Tour tour) {
        return String.format(getTemplate(), tour.getTourType(), tour.getPrice(),
                tour.getHotelType(), tour.getPeopleNumber(), tour.getTourId(), tour.getTourId());
    }
}