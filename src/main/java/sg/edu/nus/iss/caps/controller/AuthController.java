package sg.edu.nus.iss.caps.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;


import sg.edu.nus.iss.caps.exceptions.ResourceNotFoundException;
import sg.edu.nus.iss.caps.model.Admin;
import sg.edu.nus.iss.caps.service.AdminService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AdminService srvcAdmin;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/login/admin")
    public ResponseEntity<String> handleLogin(@RequestBody Admin admin, HttpSession session, HttpServletResponse response){
        try{
            Admin existingAdmin = srvcAdmin.getAdminByUsername(admin.getUsername());

            if(passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())){
                session.setAttribute("username", existingAdmin.getUsername());
                session.setAttribute("role", "admin");
                String sessionId = session.getId();
                Cookie sessionCookie = new Cookie("sessionId", sessionId);
//                sessionCookie.setPath("/");
//                sessionCookie.setMaxAge(10);
                response.addCookie(sessionCookie);

                return new ResponseEntity<String>(existingAdmin.getUsername(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>(existingAdmin.getUsername(), HttpStatus.UNAUTHORIZED);
            }
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> handleLogout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<String>("Logged Out!",HttpStatus.OK);

    }
}
