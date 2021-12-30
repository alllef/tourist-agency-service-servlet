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
import java.util.Optional;

public class ClientTourRequestCommand extends HtmlCommand {
    private final ClientService clientService;

    public ClientTourRequestCommand() {
        super(HtmlTemplate.CLIENT_TOUR_REQUEST);
        clientService = ClientService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        super.execute(request, response);
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        var tourRequests = clientService.getRequestsWithToursByUser(user);

        PrintWriter out = response.getWriter();
        for (TourRequest tourRequest : tourRequests.keySet())
            out.println(formTemplate(tourRequest, tourRequests.get(tourRequest)));

        return Optional.empty();
    }

    private String formTemplate(TourRequest tourRequest, Tour tour) {
        int finalPrice = ((100 - tourRequest.getDiscount()) * tour.getPrice()) / 100;
        return String.format(getTemplate(), tour.getTourType().toString(), tour.getPrice(), tour.getHotelType().toString(),
                tour.getPeopleNumber(), tourRequest.getRequestStatus(), tourRequest.getDiscount(), finalPrice);
    }
}
