package sg.edu.nus.iss.caps.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        HttpSession session = request.getSession();

        String URI = request.getRequestURI();
        String[] splitURI = URI.split("/");
        System.out.println(String.format("Requesting URI: %s", URI));

        if(URI.startsWith("/admin") && (!session.getAttribute("role").equals(splitURI[1]))){
            response.setStatus(403);
        }

        if(URI.startsWith("/lecturer") && (!session.getAttribute("role").equals(splitURI[1]))){
            response.setStatus(403);
        }

        if(URI.startsWith("/student") && (!session.getAttribute("role").equals(splitURI[1]))){
            response.setStatus(403);
        }

        return true;
    }
}
