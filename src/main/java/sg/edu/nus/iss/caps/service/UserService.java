package sg.edu.nus.iss.caps.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.model.Admin;
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.model.LoginUser;
import sg.edu.nus.iss.caps.model.Student;
import sg.edu.nus.iss.caps.repository.AdminRepository;
import sg.edu.nus.iss.caps.repository.LecturerRepository;
import sg.edu.nus.iss.caps.repository.StudentRepository;

import java.util.Optional;

/**
 * @Author: Cooper Liu
 * @Description: Security login service
 * @Date: Created at 9:08 2023/6/19
 * @Modified by:
 */

@Service
public class UserService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private static final String STUDENT_INIT = "S";
    private static final String LECTURER_INIT = "L";
    private static final String ADMIN_INIT = "A";
    private static final String STUDENT_ROLE = "sys:student";
    private static final String LECTURER_ROLE = "sys:lecturer";
    private static final String ADMIN_ROLE = "sys:admin";

    @Autowired
    public UserService(StudentRepository studentRepository,
                       AdminRepository adminRepository,
                       LecturerRepository lecturerRepository) {
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.lecturerRepository = lecturerRepository;
    }

    public Optional<LoginUser> getUserByUsername(String username) {
        LoginUser loginUser = new LoginUser();
        // get type of user
        if (username.startsWith(STUDENT_INIT)) {
            loginUser.setRole(STUDENT_ROLE);
            // get record from db
            Student student = studentRepository.getStudentByMatriculationNumber(username);
            // if no record, return an empty obj
            if (student == null) {
                return Optional.empty();
            }
            // or, set student's credentials
            loginUser.setUserId(student.getStudentId());
            loginUser.setUsername(student.getMatriculationNumber());
            loginUser.setPassword(student.getPassword());
        } else if (username.startsWith(LECTURER_INIT)) {
            loginUser.setRole(LECTURER_ROLE);
            Lecturer lecturer = lecturerRepository.getLecturerByMatriculationNumber(username);
            if (lecturer == null) {
                return Optional.empty();
            }
            loginUser.setUserId(lecturer.getLecturerId());
            loginUser.setUsername(lecturer.getMatriculationNumber());
            loginUser.setPassword(lecturer.getPassword());
        } else if (username.startsWith(ADMIN_INIT)) {
            loginUser.setRole(ADMIN_ROLE);
            Admin admin = adminRepository.getAdminByUsername(username);
            if (admin == null) {
                return Optional.empty();
            }
            loginUser.setUserId(admin.getAdminId());
            loginUser.setUsername(admin.getAdminUsername());
            loginUser.setPassword(admin.getAdminPassword());
        } else {
            return Optional.empty();
        }
        return Optional.of(loginUser);
    }
}
