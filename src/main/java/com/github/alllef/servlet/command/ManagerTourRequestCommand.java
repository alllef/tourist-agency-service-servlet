package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.model.service.ManagerService;
import com.github.alllef.utils.enums.HtmlTemplate;
import com.github.alllef.utils.enums.RequestStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class ManagerTourRequestCommand extends HtmlCommand {
    private final ClientService clientService;
    private final ManagerService managerService;

    public ManagerTourRequestCommand() {
        super(HtmlTemplate.MANAGER_TOUR_REQUEST);
        this.clientService = ClientService.getInstance();
        this.managerService = ManagerService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        super.execute(request, response);
        PrintWriter out = response.getWriter();
        if (request.getMethod().equals("POST"))
            updateTourRequest(request);

        var tourRequests = clientService.getRequestsWithTours();
        for (TourRequest tourRequest : tourRequests.keySet())
            out.println(formTemplate(tourRequest, tourRequests.get(tourRequest)));
        return Optional.empty();
    }

    public void updateTourRequest(HttpServletRequest req) throws SQLException {
        long tourRequestId = Long.parseLong(req.getParameter("update-tour-request"));
        RequestStatus requestStatus = RequestStatus.valueOf(req.getParameter("status"));
        int discount = Integer.parseInt(req.getParameter("discount"));

            managerService.updateTourRequest(tourRequestId, discount, requestStatus);
    }

    private String formTemplate(TourRequest tourRequest, Tour tour) {
        int finalPrice = ((100 - tourRequest.getDiscount()) * tour.getPrice()) / 100;
        String registeredSelect = "";
        String paidSelect = "";
        String canceledSelect = "";
        String selected = "selected";

        switch (tourRequest.getRequestStatus()) {
            case REGISTERED -> registeredSelect = selected;
            case PAID -> paidSelect = selected;
            case CANCELED -> canceledSelect = selected;
        }

        return String.format(getTemplate(), tour.getTourType().toString(), tour.getPrice(), tour.getHotelType().toString(),
                tour.getPeopleNumber(), tourRequest.getRequestStatus(), tourRequest.getDiscount(), registeredSelect, paidSelect, canceledSelect, tourRequest.getTourRequestId(), finalPrice);
    }
}
