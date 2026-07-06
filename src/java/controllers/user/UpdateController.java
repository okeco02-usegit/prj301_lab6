package controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;
import models.dto.User;
import models.dto.UserError;
import utils.AuthUtils;

@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (!AuthUtils.isAdmin(request)) {
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String userName = trim(request.getParameter("userName"));
        String password = trim(request.getParameter("password"));
        String lastName = trim(request.getParameter("lastName"));
        boolean admin = request.getParameter("admin") != null;
        String searchValue = request.getParameter("searchValue");

        UserError error = new UserError();
        boolean valid = true;

        if (password.length() < 3 || password.length() > 20) {
            valid = false;
            error.setPasswordError("Password must be 3-20 characters.");
        }

        if (lastName.length() < 2 || lastName.length() > 50) {
            valid = false;
            error.setLastNameError("Last name must be 2-50 characters.");
        }

        User user = new User(userName, password, lastName, admin);

        if (!valid) {
            request.setAttribute("ERROR_DETAIL", error);
            request.setAttribute("USER_DETAIL", user);
            request.setAttribute("searchValue", searchValue);
            request.getRequestDispatcher("UserDetails.jsp")
                    .forward(request, response);
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            boolean result = dao.update(user);
            request.setAttribute("message",
                    result ? "Update user successful." : "User not found.");
            request.setAttribute("USER_DETAIL", dao.find(userName));
            request.setAttribute("searchValue", searchValue);

        } catch (Exception e) {
            log("Update error", e);
            request.setAttribute("message", "Update failed.");
            request.setAttribute("USER_DETAIL", user);
            request.setAttribute("searchValue", searchValue);
        }

        request.getRequestDispatcher("UserDetails.jsp")
                .forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
