package controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;
import models.dto.User;
import models.dto.UserError;
import utils.AuthUtils;

@WebServlet("/CreateController")
public class CreateController extends HttpServlet {

    protected void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (!AuthUtils.isAdmin(request)) {
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");
            return;
        }

        String action = request.getParameter("action");

        if (!"Create".equals(action)) {
            request.getRequestDispatcher("CreateUser.jsp")
                    .forward(request, response);
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String userName = trim(request.getParameter("userName"));
        String password = trim(request.getParameter("password"));
        String lastName = trim(request.getParameter("lastName"));
        boolean admin = request.getParameter("admin") != null;

        UserError error = new UserError();
        boolean valid = true;

        if (!userName.matches("[A-Za-z0-9]{3,15}")) {
            valid = false;
            error.setUserNameError("Username must be 3-15 letters or numbers.");
        }

        if (password.length() < 3 || password.length() > 20) {
            valid = false;
            error.setPasswordError("Password must be 3-20 characters.");
        }

        if (lastName.length() < 2 || lastName.length() > 50) {
            valid = false;
            error.setLastNameError("Last name must be 2-50 characters.");
        }

        try {
            UserDAO dao = new UserDAO();

            if (valid && dao.exists(userName)) {
                valid = false;
                error.setDuplicateUserName("Username already exists.");
            }

            if (valid) {
                dao.insert(new User(userName, password, lastName, admin));
                request.setAttribute("message", "Create user successful.");
            } else {
                request.setAttribute("ERROR_DETAIL", error);
                request.setAttribute("userName", userName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("admin", admin);
            }

        } catch (Exception e) {
            log("Create error", e);
            request.setAttribute("message", "Create user failed.");
        }

        request.getRequestDispatcher("CreateUser.jsp")
                .forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
