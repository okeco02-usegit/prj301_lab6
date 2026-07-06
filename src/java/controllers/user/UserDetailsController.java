package controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;
import models.dto.User;
import utils.AuthUtils;

@WebServlet("/UserDetailsController")
public class UserDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (!AuthUtils.isAdmin(request)) {
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");
            return;
        }

        String userName = request.getParameter("userName");

        try {
            User user = new UserDAO().find(userName);

            if (user == null) {
                request.getSession().setAttribute(
                        "FLASH_MESSAGE", "User not found.");
                response.sendRedirect(request.getContextPath()
                        + "/UserController?action=Search");
                return;
            }

            request.setAttribute("USER_DETAIL", user);
            request.setAttribute("searchValue",
                    request.getParameter("searchValue"));
            request.getRequestDispatcher("UserDetails.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            log("Details error", e);
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");
        }
    }
}
