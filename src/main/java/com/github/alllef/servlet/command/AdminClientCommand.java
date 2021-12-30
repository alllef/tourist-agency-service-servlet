package com.github.alllef.servlet.command;

import com.github.alllef.model.entity.User;
import com.github.alllef.model.service.AdminService;
import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class AdminClientCommand extends HtmlCommand {
    private final AdminService adminService;

    public AdminClientCommand() {
        super(HtmlTemplate.ADMIN_USERS_CATALOGUE);
        this.adminService = AdminService.getInstance();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        super.execute(request, response);
        if (request.getMethod().equals("POST"))
            blockUser(request.getParameter("block-user"));
        PrintWriter out = response.getWriter();
        for (User user : adminService.getClients())
            out.println(formTemplate(user));

        return Optional.empty();
    }

    private void blockUser(String userParameter) {
        long userId = Long.parseLong(userParameter);

        try {
            adminService.setUserBlocked(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String formTemplate(User user) {
        String blocked = "Unblock";
        if (!user.isBlocked())
            blocked = "Block";

        return String.format(getTemplate(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserId(), blocked);
    }
}
