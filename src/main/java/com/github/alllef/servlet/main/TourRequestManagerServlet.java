package com.github.alllef.servlet.main;

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
    private static final String managerRequestCatalogueTemplate = """
                        <b>Type: </b>%s<br>
                        <b>Price: </b>%d<br>
                        <b>Hotel type: </b>%s<br>
                        <b>Number of people: </b>%d<br>
                        <b>Status: </b>%s<br>
                        <form id="manager-update-tour-request" action="tour-requests" method="post">
                        <h3>Discount</h3>
                           <input id="discount" name="discount" value="%d" type="number"/>
                           <h3>Status</h3>
                           <select form="manager-update-tour-request" name="status" id="status">
                               <option value="REGISTERED" %s>Registered</option>
                               <option value="PAID" %s>Paid</option>
                               <option value = "CANCELED" %s>Canceled</option>
                           </select>
                       
                       <button type="submit" name="update-tour-request" value ="%d" title="update-tour-request">Update request</button>
                        </form>
                        <b>Final price: </b>%d <br>
            """;

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