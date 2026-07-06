package controllers.user;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;
import models.dto.User;
import utils.AuthUtils;

@WebServlet("/DeleteController")
public class DeleteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (!AuthUtils.isAdmin(request)) {
            response.sendRedirect(request.getContextPath()
                    + "/UserController?action=Search");
            return;
        }

        String userName = request.getParameter("userName");
        String searchValue = request.getParameter("searchValue");
        User loginUser = AuthUtils.getLoginUser(request);

        try {
            if (loginUser != null
                    && loginUser.getUserName().equals(userName)) {
                request.getSession().setAttribute(
                        "FLASH_MESSAGE", "You cannot delete your own account.");
            } else {
                boolean result = new UserDAO().delete(userName);
                request.getSession().setAttribute(
                        "FLASH_MESSAGE",
                        result ? "Delete user successful." : "User not found.");
            }
        } catch (Exception e) {
            log("Delete error", e);
            request.getSession().setAttribute(
                    "FLASH_MESSAGE", "Delete failed.");
        }

        if (searchValue == null) searchValue = "";

        response.sendRedirect(request.getContextPath()
                + "/UserController?action=Search&searchValue="
                + URLEncoder.encode(searchValue, "UTF-8"));
    }
}
