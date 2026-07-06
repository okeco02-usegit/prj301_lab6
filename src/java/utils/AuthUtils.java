package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.dto.User;

public class AuthUtils {
    public static User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object value = session.getAttribute("USER_LOGGED_IN");
        return value instanceof User ? (User) value : null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        User user = getLoginUser(request);
        return user != null && user.isAdmin();
    }
}
