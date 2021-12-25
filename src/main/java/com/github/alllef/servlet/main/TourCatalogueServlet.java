package com.github.alllef.servlet.main;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.service.ClientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/tours/catalogue")
public class TourCatalogueServlet extends HttpServlet {
    private static final String staticTemplate = """
            <html>
            <head>
                <title>Title</title>
            </head>
            <body>
            <h1>Tour catalogue</h1><br>
            %s
            <form id="catalogue-filter" action="catalogue" method="post">
                <h3>Tour type</h3>
                <select form="catalogue-filter" name="tour-type" id="tour-type">
                    <option value="REST">Rest</option>
                    <option value="EXCURSION">Excursion</option>
                    <option value="SHOPPING">Shopping</option>
                </select>
               
                <h3>Min price</h3>
                <input id="min-price" name="min-price" type="number"/>
                <h3>Max price</h3>
                <input id="max-price" name="max-price" type="number"/>
                <h3>Min people number</h3>
                <input id="min-people-number" name="min-people-number" type="number"/>
                <h3>Max people number</h3>
                <input id="max-people-number" name="max-people-number" type="number"/>
                <h3>Hotel type</h3>
                <select form="catalogue-filter" name="hotel-type" id="hotel-type">
                    <option value="HOSTEL">Hostel</option>
                    <option value="ALL_INCLUSIVE">All inclusive</option>
                </select>
                <button type="submit" name="" value="Filter" title="Filter">Filter</button>
            </form>
                      
            </body>
            </html>""";

    private static final String catalogueTemplate = """
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>""";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        htmlWithTours(resp.getWriter(), req.getParameterMap());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
         htmlWithTours(resp.getWriter(), req.getParameterMap());
    }

    public void htmlWithTours(PrintWriter writer, Map<String, String[]> params) {
        ClientService client = new ClientService(new TourDAO(ConnectionSingleton.getConnection()), new TourRequestDAO(ConnectionSingleton.getConnection()), new UserDAO(ConnectionSingleton.getConnection()));
        StringBuilder resultsPeople = new StringBuilder();
        for (Tour tour : client.filterTours(params))
            resultsPeople.append(String.format(catalogueTemplate, tour.getTourType(), tour.getPrice(), tour.getHotelType(), tour.getPeopleNumber()));

        try (writer; writer) {
            String resultString = String.format(staticTemplate, resultsPeople);
            writer.println(resultString);
        }
    }

}