package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.ClientService;
import com.github.alllef.utils.enums.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class RegistrationCommand implements ServletCommand {
    private final ClientService clientService;

   public RegistrationCommand() {
        this.clientService = ClientService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        if (request.getMethod().equals("GET"))
            return Optional.of("/main/register.jsp");

        String email = request.getParameter("email");
        Optional<User> userOpt = clientService.findByEmail(email);
        if (userOpt.isPresent()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("User already exists");
        } else {
            User user = User.builder().userType(UserType.CLIENT)
                    .password(request.getParameter("password"))
                    .email(request.getParameter("email"))
                    .firstName(request.getParameter("first-name"))
                    .lastName(request.getParameter("last-name"))
                    .build();
            clientService.createClient(user);
        }

        return Optional.empty();
    }
}
