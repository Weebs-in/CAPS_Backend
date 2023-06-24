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
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.model.Student;
import sg.edu.nus.iss.caps.service.AdminService;
import sg.edu.nus.iss.caps.service.LecturerService;
import sg.edu.nus.iss.caps.service.StudentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AdminService srvcAdmin;

    @Autowired
    private LecturerService srvcLecturer;

    @Autowired
    private StudentService srvcStudent;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/login/admin")
    public ResponseEntity<?> handleLogin(@RequestBody Admin admin, HttpSession session, HttpServletResponse response){
        try{
            Admin existingAdmin = srvcAdmin.getAdminByUsername(admin.getUsername());


            if(passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())){
                session.setAttribute("userId", existingAdmin.getId());
                session.setAttribute("role", "admin");
                String sessionId = session.getId();
                Cookie sessionCookie = new Cookie("sessionId", sessionId);
//                sessionCookie.setPath("/");
//                sessionCookie.setMaxAge(10);
                response.addCookie(sessionCookie);

                Map<String, String> res = new HashMap<>();
                res.put("id", existingAdmin.getId().toString());
                res.put("username", existingAdmin.getUsername());

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(admin.getUsername() + " is Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/lecturer")
    public ResponseEntity<?> handleLogin(@RequestBody Lecturer lecturer, HttpSession session, HttpServletResponse response){
        try{
            Lecturer existingLecturer = srvcLecturer.getLecturerByMatriculationNumber(lecturer.getMatriculationNumber());


            if(passwordEncoder.matches(lecturer.getPassword(), existingLecturer.getPassword())){
                session.setAttribute("userId", existingLecturer.getLecturerId());
                session.setAttribute("role", "lecturer");
                String sessionId = session.getId();
                Cookie sessionCookie = new Cookie("sessionId", sessionId);
//                sessionCookie.setPath("/");
//                sessionCookie.setMaxAge(10);
                response.addCookie(sessionCookie);

                Map<String, String> res = new HashMap<>();
                res.put("id", existingLecturer.getLecturerId().toString());
                res.put("matricNum", existingLecturer.getMatriculationNumber());
                res.put("firstMidName", existingLecturer.getFirstMidName());
                res.put("lastName", existingLecturer.getLastName());
                res.put("gender", existingLecturer.getGender());
                res.put("dob", existingLecturer.getDateOfBirth().toString());

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(lecturer.getMatriculationNumber() + " is Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/student")
    public ResponseEntity<?> handleLogin(@RequestBody Student student, HttpSession session, HttpServletResponse response){
        try{
            Student existingStudent = srvcStudent.getStudentByMatriculationNumber(student.getMatriculationNumber());


            if(passwordEncoder.matches(student.getPassword(), existingStudent.getPassword())){
                session.setAttribute("userId", existingStudent.getStudentId());
                session.setAttribute("role", "student");
                String sessionId = session.getId();
                Cookie sessionCookie = new Cookie("sessionId", sessionId);
//                sessionCookie.setPath("/");
//                sessionCookie.setMaxAge(10);
                response.addCookie(sessionCookie);

                Map<String, String> res = new HashMap<>();
                res.put("id", existingStudent.getStudentId().toString());
                res.put("matricNum", existingStudent.getMatriculationNumber());
                res.put("firstMidName", existingStudent.getFirstMidName());
                res.put("lastName", existingStudent.getLastName());
                res.put("gender", existingStudent.getGender());
                res.put("dob", existingStudent.getDateOfBirth().toString());
                res.put("gpa", existingStudent.getGpa().toString());

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(student.getMatriculationNumber() + " is Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> handleLogout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged Out!",HttpStatus.OK);

    }
}
