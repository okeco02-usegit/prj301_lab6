package controllers.user;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import utils.AuthUtils;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

    protected void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (AuthUtils.getLoginUser(request) == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String url = "/SearchController";

        if ("CreatePage".equals(action) || "Create".equals(action)) {
            url = "/CreateController";
        } else if ("Details".equals(action)) {
            url = "/UserDetailsController";
        } else if ("Update".equals(action)) {
            url = "/UpdateController";
        } else if ("Delete".equals(action)) {
            url = "/DeleteController";
        } else if ("Logout".equals(action)) {
            url = "/LogoutController";
        }

        request.getRequestDispatcher(url).forward(request, response);
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
