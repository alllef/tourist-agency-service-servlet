package com.github.alllef.servlet.main;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.AdminService;
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
import java.util.List;

@WebServlet("/admin/users")
public class UserAdminServlet extends HttpServlet {
    private static final String adminUsersCatalogueTemplate = """
                        <b>First name: </b>%s<br>
                        <b>Last name: </b>%s<br>
                        <b>Email</b>%s<br>
                        <form id="user-block-request" action="users" method="post">
                       
                       <button type="submit" name="block-user" value ="%d" title="block-user">%s</button>
                        </form>
            """;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminService admin = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());

        PrintWriter out = resp.getWriter();

        List<User> clients = admin.getClients();
        for (User user : clients)
            out.println(formTemplate(user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DaoFactory daoFactory = DaoFactory.getInstance();
        PrintWriter out = resp.getWriter();
        AdminService admin = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        long userId = Long.parseLong(req.getParameter("block-user"));

        try {
           admin.setUserBlocked(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<User> clients = admin.getClients();
        for (User user : clients)
            out.println(formTemplate(user));
    }

    private String formTemplate(User user) {
        String blocked = "Unblock";
        if (!user.isBlocked())
            blocked = "Block";

        return String.format(adminUsersCatalogueTemplate, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserId(), blocked);
    }
}
