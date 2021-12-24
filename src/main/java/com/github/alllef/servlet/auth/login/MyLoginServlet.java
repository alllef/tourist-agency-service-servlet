
package com.github.alllef.servlet.auth.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public final class MyLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 3329450219328582888L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("It's happened");
        request.getRequestDispatcher("/security/login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
        }

        // Do your crazy complex login procedure here
        if (username.equalsIgnoreCase("test") && password.equalsIgnoreCase("test")) {
            request.getSession().setAttribute("user", new MyCustomPrincipal(username, Arrays.asList("manager", "admin")));
            System.out.println("Principal is: " + request.getSession(false).getAttribute("user"));
        } else {
            // what should happen when login fails
            try {
                throw new LoginException("You're not test=test user !");
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("logged so");
        response.sendRedirect(request.getContextPath() + "/logged");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();
    }

}
