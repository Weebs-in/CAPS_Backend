package sg.edu.nus.iss.caps.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        HttpSession session = request.getSession();

        //check if the session attribute "username" and "matricNum" already has value
        if(session.getAttribute("username") != null || session.getAttribute("matricNum") != null) {
            return true;
        }

        //Check the user is going to login page
        String[] splitURI = request.getRequestURI().split("/");
        if(splitURI[splitURI.length - 2].equals("login")) {
            return true;
        }

        //If the user has not logged in, redirect to login page
        response.setStatus(302);
//		response.addHeader("Location", "/api/auth/login");
        return false;
    }
}
