package com.github.alllef.servlet.main.manager;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.model.service.ManagerService;
import com.github.alllef.utils.enums.RequestStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/managing/tour-requests")
public class TourRequestManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());

        PrintWriter out = resp.getWriter();

        var tourRequests = client.getRequestsWithTours();
        for (TourRequest tourRequest : tourRequests.keySet())
            out.println(formTemplate(tourRequest, tourRequests.get(tourRequest)));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("What the hell");
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        PrintWriter out = resp.getWriter();
        ManagerService managerService = new ManagerService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO());
        long tourRequestId = Long.parseLong(req.getParameter("update-tour-request"));
        RequestStatus requestStatus = RequestStatus.valueOf(req.getParameter("status"));
        int discount = Integer.parseInt(req.getParameter("discount"));

        try {
            managerService.updateTourRequest(tourRequestId, discount, requestStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        var tourRequests = client.getRequestsWithTours();
        for (TourRequest tourRequest : tourRequests.keySet())
            out.println(formTemplate(tourRequest, tourRequests.get(tourRequest)));
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

        return String.format(managerRequestCatalogueTemplate, tour.getTourType().toString(), tour.getPrice(), tour.getHotelType().toString(),
                tour.getPeopleNumber(), tourRequest.getRequestStatus(), tourRequest.getDiscount(), registeredSelect, paidSelect, canceledSelect, tourRequest.getTourRequestId(), finalPrice);
    }
}