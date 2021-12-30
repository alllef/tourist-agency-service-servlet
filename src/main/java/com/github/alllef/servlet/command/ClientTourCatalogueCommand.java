package com.github.alllef.servlet.command;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class ClientTourCatalogueCommand extends HtmlCommand {
    private final ClientService clientService;

    public ClientTourCatalogueCommand() {
        super(HtmlTemplate.CLIENT_TOUR_CATALOGUE);
        clientService = ClientService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.execute(request, response);
        if (request.getMethod().equals("POST")) {
            orderTour(request);
            return Optional.of("/main/tour-catalogue.jsp");
        }

        PrintWriter out = response.getWriter();
        for (Tour tour : clientService.filterTours(request.getParameterMap()))
            out.println(formTemplate(tour));
        return Optional.empty();
    }

    public void orderTour(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        long tourId = Long.parseLong(request.getParameter("order"));
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        client.orderTour(tourId, user.getUserId());
    }

    public String formTemplate(Tour tour) {
        return String.format(getTemplate(), tour.getTourType(), tour.getPrice(), tour.getHotelType(), tour.getPeopleNumber(), tour.getTourId());
    }
}
