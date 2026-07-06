package controllers.user;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.dao.UserDAO;

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {

    protected void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String value = request.getParameter("searchValue");
        if (value == null) value = "";

        try {
            request.setAttribute("SEARCH_RESULT",
                    new UserDAO().searchByLastName(value.trim()));
            request.setAttribute("searchValue", value);
        } catch (Exception e) {
            log("Search error", e);
            request.setAttribute("message", "Search failed.");
        }

        request.getRequestDispatcher("Search.jsp").forward(request, response);
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
