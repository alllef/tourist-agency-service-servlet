package com.github.alllef.servlet.auth.login;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoFactory daoFactory = DaoFactory.getInstance();

        ClientService client = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        String email = req.getParameter("email");
        Optional<User> userOpt = client.findByEmail(email);
        if (userOpt.isPresent()) {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("User already exists");
            out.close();
        } else {
            User user = User.builder().userType(UserType.CLIENT)
                    .password(req.getParameter("password"))
                    .email(req.getParameter("email"))
                    .firstName(req.getParameter("first-name"))
                    .lastName(req.getParameter("last-name"))
                    .build();
            client.createUser(user);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/main/register.jsp").forward(req, resp);
    }

}
