package com.github.alllef.servlet.command;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

public class ClientTourCatalogueCommand extends HtmlCommand {
    ClientService clientService;

    public ClientTourCatalogueCommand() {
        super(HtmlTemplate.CLIENT_TOUR_CATALOGUE);
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.execute(request, response);

        PrintWriter out = response.getWriter();
        for (Tour tour : clientService.filterTours(request.getParameterMap()))
            out.println(formTemplate(tour));
        return Optional.empty();
    }

    public String formTemplate(Tour tour) {
        return String.format(getTemplate(), tour.getTourType(), tour.getPrice(), tour.getHotelType(), tour.getPeopleNumber(), tour.getTourId());
    }
}
