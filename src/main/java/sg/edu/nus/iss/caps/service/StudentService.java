package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.Faculty;
import sg.edu.nus.iss.caps.model.Student;
import sg.edu.nus.iss.caps.repository.FacultyRepository;
import sg.edu.nus.iss.caps.repository.StudentRepository;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 23:17 2023/6/14
 * @Modified by:
 */

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public R saveStudent(Student student) {
        // First, we check if the faculty_id is valid
        Faculty faculty = facultyRepository.findById(student.getFaculty().getFacultyId()).orElse(null);
        // if the faculty does not exist, then refuse the request
        if (faculty == null) {
            return R.error(RMessage.CREATE_FAILED + ": Faculty not found");
        }
        studentRepository.save(student);
        return R.ok(RMessage.CREATE_SUCCESS);
    }

    public R getAllStudents() {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", studentRepository.findAll());
    }

    public R getStudentsByFacultyId(Long facultyId) {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", studentRepository.getStudentsByFacultyId(facultyId));
    }

    public R getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", student);
    }

    public R updateStudent(Student student) {
        // First check student
        Student isStudent = studentRepository.findById(student.getStudentId()).orElse(null);
        if (isStudent == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Student not found");
        }
        // then check faculty
        Faculty isFaculty = facultyRepository.findById(student.getFaculty().getFacultyId()).orElse(null);
        if (isFaculty == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Faculty not found");
        }
        // All fine, then save
        studentRepository.save(student);
        return R.ok(RMessage.UPDATE_SUCCESS);
    }

    public R deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
        return R.ok(RMessage.DELETE_SUCCESS);
    }

    public R updateGpaById(Long studentId, Double gpa) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.RETRIEVE_FAILED);
        }
        student.setGpa(gpa);
        studentRepository.save(student);
        return R.ok(RMessage.UPDATE_SUCCESS);
    }
}
