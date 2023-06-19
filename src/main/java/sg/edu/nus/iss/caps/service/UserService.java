package sg.edu.nus.iss.caps.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.model.LoginUser;
import sg.edu.nus.iss.caps.model.Student;
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

    private final StudentRepository studentRepository;
    private static final String STUDENT_INIT = "S";
    private static final String LECTURER_INIT = "L";
    private static final String ADMIN_INIT = "A";
    private static final String STUDENT_ROLE = "sys:student";
    private static final String LECTURER_ROLE = "sys:lecturer";
    private static final String ADMIN_ROLE = "sys:admin";

    @Autowired
    public UserService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
            throw new NotImplementedException("Lecturer module not implemented");
        } else if (username.startsWith(ADMIN_INIT)) {
            loginUser.setRole(ADMIN_ROLE);
            // throw new NotImplementedException("Admin module not implemented");
            // TODO: FIX THIS
            loginUser.setUserId(1L);
            loginUser.setUsername("A0000001");
            loginUser.setPassword("$2a$12$2XDfQYrtaOhyM.ICPoo0Bu8pzvtZl7lg9p7cr8.9Pvz.HDmS9gOjy");
        } else {
            return Optional.empty();
        }
        return Optional.of(loginUser);
    }
}
