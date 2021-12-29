package com.github.alllef.servlet.main.admin;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.AdminService;
import com.github.alllef.model.service.ClientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/admin/tour-catalogue")
public class TourCatalogueAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        htmlWithTours(resp.getWriter(), req.getParameterMap());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String deleteId = req.getParameter("delete");

        if (deleteId != null) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            AdminService adminService = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
            adminService.deleteTour(Long.parseLong(deleteId));
        }

        htmlWithTours(resp.getWriter(), req.getParameterMap());
       /*long tourId = Long.parseLong(req.getParameter("order"));
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());

        client.orderTour(tourId, user.getUserId());
        htmlWithTours(resp.getWriter(), req.getParameterMap());*/
    }

    public void htmlWithTours(PrintWriter writer, Map<String, String[]> params) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        StringBuilder resultsPeople = new StringBuilder();
        for (Tour tour : client.filterTours(params))
            resultsPeople.append(String.format(catalogueTemplate, tour.getTourType(), tour.getPrice(),
                    tour.getHotelType(), tour.getPeopleNumber(), tour.getTourId(), tour.getTourId()));
        writer.println(resultsPeople);
    }

}
