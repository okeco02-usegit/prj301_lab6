package controllers.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;
import models.dto.User;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (userName == null || userName.trim().isEmpty()
                || password == null || password.isEmpty()) {
            request.setAttribute("message", "Username and password are required.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        try {
            User user = new UserDAO().login(userName.trim(), password);
            if (user == null) {
                request.setAttribute("message", "Username or password is invalid.");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("USER_LOGGED_IN", user);
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");

        } catch (Exception e) {
            log("Login error", e);
            request.setAttribute("message", "Cannot connect to database.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
